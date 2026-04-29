package com.mycompany.kursach_java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;


public class TetrisClientGUI extends JFrame {
    private static final int COLS = 10, ROWS = 20, CELL = 28;

    private final List<List<Character>> board = new ArrayList<>(ROWS);
    private volatile boolean running = false;
    private int score = 0;
    private Thread gameThread;

    private char[][] piece;
    private int pieceX = 4, pieceY = 0;
    private char pieceChar = '#';

    private final ScoreManager scoreManager = new ScoreManager();
    private final String playerName = "Player1";

    private GamePanel gamePanel;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    private JLabel leaderLabel;

    public TetrisClientGUI() {
        initBoard();
        setupUI();
        initGame();
    }

    private void initBoard() {
        for (int i = 0; i < ROWS; i++) {
            board.add(new ArrayList<>(Collections.nCopies(COLS, '.')));
        }
    }

    private void setupUI() {
        setTitle("Tetris GUI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);

        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(COLS * CELL, ROWS * CELL));
        gamePanel.setBackground(Color.BLACK);
        gamePanel.setFocusable(true);
        gamePanel.setRequestFocusEnabled(true);

        // ✅ Боковая панель
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(40, 40, 40));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidebar.setFocusable(false);
        sidebar.setRequestFocusEnabled(false);

        // Счёт
        scoreLabel = createLabel("Счёт: 0");
        sidebar.add(scoreLabel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        // Статус
        statusLabel = createLabel("▶️ Игра запущена");
        sidebar.add(statusLabel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        // Кнопка новой игры
        JButton restartBtn = new JButton("🔄 Новая игра (R)");
        restartBtn.setMaximumSize(new Dimension(150, 35));
        restartBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartBtn.setFocusable(false);
        restartBtn.setRequestFocusEnabled(false);
        restartBtn.addActionListener(e -> {
            restartGame();
            gamePanel.requestFocusInWindow();
        });
        sidebar.add(restartBtn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        // Кнопка таблицы лидеров
        JButton lbBtn = new JButton("🏆 Таблица лидеров");
        lbBtn.setMaximumSize(new Dimension(150, 35));
        lbBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbBtn.setFocusable(false);
        lbBtn.setRequestFocusEnabled(false);
        lbBtn.addActionListener(e -> {
            showLeaderboard();
            gamePanel.requestFocusInWindow();
        });
        sidebar.add(lbBtn);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        // Таблица лидеров (изначально пустая)
        leaderLabel = new JLabel("", SwingConstants.CENTER);
        leaderLabel.setForeground(Color.WHITE);
        leaderLabel.setFont(new Font("Consolas", Font.PLAIN, 11));
        leaderLabel.setMaximumSize(new Dimension(150, 150));
        leaderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderLabel.setFocusable(false);
        sidebar.add(leaderLabel);

        add(gamePanel, BorderLayout.CENTER);
        add(sidebar, BorderLayout.EAST);
        pack();
        setLocationRelativeTo(null);

        // ✅ Обработчик клавиш
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    restartGame();
                    return;
                }
                if (!running) return;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> movePiece(-1, 0);
                    case KeyEvent.VK_RIGHT -> movePiece(1, 0);
                    case KeyEvent.VK_DOWN -> movePiece(0, 1);
                    case KeyEvent.VK_UP -> rotatePiece();
                    case KeyEvent.VK_SPACE -> dropPieceInstant();
                }
            }
        });

        // ✅ Фокус на игровое поле при старте
        SwingUtilities.invokeLater(() -> {
            gamePanel.requestFocusInWindow();
            gamePanel.grabFocus();
        });
    }

    // ✅ Показ таблицы лидеров
    private void showLeaderboard() {
        setStatus("📊 Загрузка...");
        
        Executors.newSingleThreadExecutor().submit(() -> {
            scoreManager.loadScores();
            List<ScoreRecord> records = scoreManager.getTopRecords(5);
            
            StringBuilder sb = new StringBuilder("<html><div style='text-align: center;'>");
            sb.append("<b>🏆 ТОП-5</b><br><br>");
            
            if (records.isEmpty()) {
                sb.append("Нет записей");
            } else {
                int rank = 1;
                for (ScoreRecord record : records) {
                    sb.append(String.format("%d. %s<br>%d очков<br><br>", 
                        rank++, record.getPlayerName(), record.getScore()));
                }
            }
            sb.append("</div></html>");
            
            String htmlContent = sb.toString();
            
            SwingUtilities.invokeLater(() -> {
                leaderLabel.setText(htmlContent);
                setStatus("▶️ Игра активна");
                gamePanel.requestFocusInWindow();
                gamePanel.grabFocus();
            });
        });
    }

    private void initGame() {
        spawnNewPiece();
        running = true;
        setStatus("▶️ Игра запущена");
        startGameLoop();
    }

    private void startGameLoop() {
        if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();
        }
        gameThread = new Thread(this::runGameLoop, "GameLoop");
        gameThread.start();
    }

    private void runGameLoop() {
        try {
            while (running) {
                movePiece(0, 1);
                SwingUtilities.invokeLater(() -> gamePanel.repaint());
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean movePiece(int dx, int dy) {
        int nx = pieceX + dx, ny = pieceY + dy;

        if (isValid(nx, ny)) {
            pieceX = nx;
            pieceY = ny;
            return true;
        } else if (dy > 0) {
            lockPiece();
            clearLines();
            spawnNewPiece();
            if (!isValid(pieceX, pieceY)) {
                gameOver();
                return false;
            }
        }
        return false;
    }

    private boolean isValid(int x, int y) {
        for (int r = 0; r < piece.length; r++) {
            for (int c = 0; c < piece[0].length; c++) {
                if (piece[r][c] == '.') continue;
                int bx = x + c, by = y + r;

                if (bx < 0 || bx >= COLS || by >= ROWS) return false;
                if (by >= 0 && board.get(by).get(bx) != '.') return false;
            }
        }
        return true;
    }

    private void lockPiece() {
        for (int r = 0; r < piece.length; r++) {
            for (int c = 0; c < piece[0].length; c++) {
                if (piece[r][c] != '.' && pieceY + r >= 0) {
                    board.get(pieceY + r).set(pieceX + c, pieceChar);
                }
            }
        }
        score += 10;
        updateScoreLabel();
    }

    private void spawnNewPiece() {
        char[][][] pieces = {
            {{'#','#','#','#'}},
            {{'#','#'}, {'#','#'}},
            {{'.','#','.'}, {'#','#','#'}},
            {{'.','#','#'}, {'#','#','.'}},
            {{'#','#','.'}, {'.','#','#'}},
            {{'#','.','.'}, {'#','#','#'}},
            {{'.','.','#'}, {'#','#','#'}}
        };

        int randomIndex = new Random().nextInt(pieces.length);
        piece = pieces[randomIndex];
        pieceChar = (char)('A' + new Random().nextInt(4));
        pieceX = COLS / 2 - piece[0].length / 2;
        pieceY = 0;

        if (!isValid(pieceX, pieceY)) {
            gameOver();
        }
    }

    private void rotatePiece() {
        char[][] rotated = new char[piece[0].length][piece.length];
        for (int r = 0; r < piece.length; r++)
            for (int c = 0; c < piece[0].length; c++)
                rotated[c][piece.length - 1 - r] = piece[r][c];

        char[][] old = piece;
        piece = rotated;

        if (!isValid(pieceX, pieceY)) {
            piece = old;
        }
    }

    private void dropPieceInstant() {
        while (isValid(pieceX, pieceY + 1)) {
            pieceY++;
        }
        lockPiece();
        clearLines();
        spawnNewPiece();
    }

    private void clearLines() {
        List<Integer> fullRowIndices = java.util.stream.IntStream.range(0, ROWS)
                .filter(i -> board.get(i).stream().allMatch(c -> c != '.'))
                .boxed()
                .collect(java.util.stream.Collectors.toList());

        if (fullRowIndices.isEmpty()) return;

        for (int i = fullRowIndices.size() - 1; i >= 0; i--) {
            board.remove((int) fullRowIndices.get(i));
        }

        for (int i = 0; i < fullRowIndices.size(); i++) {
            board.add(0, new ArrayList<>(Collections.nCopies(COLS, '.')));
        }

        int cleared = fullRowIndices.size();
        score += cleared * 100 * cleared;
        updateScoreLabel();
        setStatus("🔥 Линий: " + cleared);
    }

    private void gameOver() {
        running = false;
        setStatus("💥 Игра окончена!");

        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                scoreManager.addRecord(playerName, score);
                SwingUtilities.invokeLater(() -> {
                    setStatus("✅ Сохранено! Нажмите R");
                    updateLeaderboardDisplay();
                });
            } catch (TetrisException e) {
                SwingUtilities.invokeLater(() ->
                    setStatus("❌ Ошибка: " + e.getMessage())
                );
            }
        });
    }

    public void restartGame() {
        running = false;
        if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();
        }

        score = 0;
        running = true;
        board.clear();
        for (int i = 0; i < ROWS; i++) {
            board.add(new ArrayList<>(Collections.nCopies(COLS, '.')));
        }
        spawnNewPiece();
        updateScoreLabel();
        setStatus("🔄 Новая игра!");
        leaderLabel.setText(""); // Очищаем таблицу при рестарте
        
        startGameLoop();
        SwingUtilities.invokeLater(() -> {
            gamePanel.requestFocusInWindow();
            gamePanel.grabFocus();
        });
    }

    private void updateLeaderboardDisplay() {
        List<ScoreRecord> records = scoreManager.getTopRecords(3);
        if (!records.isEmpty()) {
            StringBuilder sb = new StringBuilder("<html><div style='text-align: center; font-size: 10px;'>");
            sb.append("<b>🏆 ТОП</b><br>");
            int rank = 1;
            for (ScoreRecord record : records) {
                sb.append(String.format("%d. %s - %d<br>", 
                    rank++, record.getPlayerName(), record.getScore()));
            }
            sb.append("</div></html>");
            leaderLabel.setText(sb.toString());
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setMaximumSize(new Dimension(150, 25));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFocusable(false);
        return label;
    }

    private void updateScoreLabel() {
        SwingUtilities.invokeLater(() -> 
            scoreLabel.setText("Счёт: " + score)
        );
    }

    private void setStatus(String msg) {
        SwingUtilities.invokeLater(() -> 
            statusLabel.setText(msg)
        );
    }

    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Рисуем сетку
            g.setColor(new Color(30, 30, 30));
            for (int i = 0; i <= COLS; i++) {
                g.drawLine(i * CELL, 0, i * CELL, ROWS * CELL);
            }
            for (int i = 0; i <= ROWS; i++) {
                g.drawLine(0, i * CELL, COLS * CELL, i * CELL);
            }

            // Рисуем зафиксированные блоки
            for (int y = 0; y < ROWS; y++) {
                for (int x = 0; x < COLS; x++) {
                    char c = board.get(y).get(x);
                    if (c != '.') {
                        g.setColor(getColorFor(c));
                        g.fillRect(x * CELL + 1, y * CELL + 1, CELL - 2, CELL - 2);
                        g.setColor(getColorFor(c).brighter());
                        g.fillRect(x * CELL + 1, y * CELL + 1, CELL - 2, 3);
                    }
                }
            }

            // Рисуем текущую фигуру
            if (piece != null) {
                for (int r = 0; r < piece.length; r++) {
                    for (int c = 0; c < piece[0].length; c++) {
                        if (piece[r][c] != '.' && pieceY + r >= 0) {
                            int drawX = (pieceX + c) * CELL;
                            int drawY = (pieceY + r) * CELL;
                            g.setColor(getColorFor(pieceChar));
                            g.fillRect(drawX + 1, drawY + 1, CELL - 2, CELL - 2);
                            g.setColor(getColorFor(pieceChar).brighter());
                            g.fillRect(drawX + 1, drawY + 1, CELL - 2, 3);
                        }
                    }
                }
            }
        }

        private Color getColorFor(char c) {
            return switch (c) {
                case 'A' -> new Color(255, 50, 50);
                case 'B' -> new Color(50, 255, 50);
                case 'C' -> new Color(50, 50, 255);
                case 'D' -> new Color(255, 255, 50);
                default -> new Color(255, 0, 255);
            };
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TetrisClientGUI().setVisible(true));
    }
}