package com.mycompany.kursach_java;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class TetrisServer {
    private static final int PORT = 8080;
    private final ConcurrentHashMap<String, Integer> scoreboard = new ConcurrentHashMap<>();
    private final ExecutorService pool = Executors.newFixedThreadPool(5);

    public void start() throws TetrisException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("🟢 Сервер запущен на порту " + PORT);
            while (true) {
                Socket client = server.accept();
                pool.submit(() -> handleClient(client));
            }
        } catch (IOException e) {
            throw new TetrisException("Server startup failed", e);
        }
    }

    private void handleClient(Socket client) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
            
            String line;
            while ((line = in.readLine()) != null) {
                String[] cmd = line.trim().split("\\s+");
                switch (cmd[0].toUpperCase()) {
                    case "SCORE" -> updateScore(cmd[1], Integer.parseInt(cmd[2]), out);
                    case "LEADERBOARD" -> sendLeaderboard(out);
                    case "QUIT" -> { out.println("BYE"); return; }
                    default -> throw new TetrisException("Unknown command: " + cmd[0]);
                }
            }
        } catch (TetrisException e) {
            System.err.println("⚠️ Client error: " + e.getMessage());
        } catch (IOException | NumberFormatException e) {
            System.err.println("⚠️ IO/Format error: " + e.getMessage());
        } finally {
            try { client.close(); } catch (IOException ignored) {}
        }
    }

    private void updateScore(String name, int score, PrintWriter out) throws TetrisException {
        if (score < 0 || name.length() > 10) throw new TetrisException("Invalid score data");
        scoreboard.merge(name, score, Math::max);
        out.println("OK");
    }

    private void sendLeaderboard(PrintWriter out) {
        String top = scoreboard.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .map(e -> String.format("%-10s %d", e.getKey(), e.getValue()))
                .collect(Collectors.joining("\n"));
        out.println("LEADERBOARD\n" + (top.isEmpty() ? "Нет записей" : top));
    }

    public static void main(String[] args) throws TetrisException {
        new TetrisServer().start();
    }
}