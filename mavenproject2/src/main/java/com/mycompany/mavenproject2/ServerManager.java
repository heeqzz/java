package com.mycompany.mavenproject2;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Менеджер сервера.
 * Отвечает за прием подключений, распределение частей интервала между клиентами
 * и агрегацию результатов вычислений.
 */
public class ServerManager {
    private static final Logger logger = Logger.getLogger(ServerManager.class.getName());
    private int port;
    private ServerSocket serverSocket;
    private volatile boolean running = false;
    
    private final List<ClientConnection> clients = new ArrayList<>();
    private final ConcurrentLinkedQueue<TaskInfo> waitingTasks = new ConcurrentLinkedQueue<>();
    
    // Хранит частичные результаты для каждой задачи: TaskID -> (ClientIndex -> Result)
    private final ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Double>> partialResultsMap = new ConcurrentHashMap<>();
    
    private static final int REQUIRED_CLIENTS = 6;
    private JFrame parentFrame;

    public interface ServerCallback {
        void onClientConnected(String ip);
        void onTaskCompleted(int taskId, double result);
        void onTaskFailed(int taskId, String error);
    }
    private ServerCallback callback;

    public ServerManager(int port, JFrame parentFrame, ServerCallback callback) {
        this.port = port;
        this.parentFrame = parentFrame;
        this.callback = callback;
    }

    public void start() {
        if (running) return;
        running = true;
        new Thread(this::serverLoop, "Server-Listener").start();
        logger.info("Сервер запущен на порту " + port + ". Ожидается " + REQUIRED_CLIENTS + " клиентов.");
    }

    public void stop() {
        running = false;
        
        // Корректное закры всех клиентских соединений перед остановкой сервера
        for (ClientConnection client : new ArrayList<>(clients)) {
            client.close();
        }
        clients.clear();
        
        try { 
            if (serverSocket != null) serverSocket.close(); 
        } catch (IOException e) {}
        
        logger.info("Сервер остановлен");
    }

    private void serverLoop() {
        try {
            serverSocket = new ServerSocket(port);
            while (running) {
                Socket clientSocket = serverSocket.accept();
                clientSocket.setSoTimeout(30000); 
                
                ClientConnection conn = new ClientConnection(clientSocket);
                clients.add(conn);
                
                int clientIndex = clients.size() - 1;
                conn.setClientIndex(clientIndex);

                SwingUtilities.invokeLater(() -> 
                    callback.onClientConnected(clientSocket.getInetAddress().getHostAddress() + " (Index: " + clientIndex + ")"));
                
                logger.info("Клиент #" + clientIndex + " подключён. Всего клиентов: " + clients.size());
                
                // Если набралось 6 клиентов, проверяем очередь ожидающих задач
                if (clients.size() == REQUIRED_CLIENTS) {
                    processWaitingTasks();
                }

                new Thread(conn, "Client-Handler-" + clientSocket.getPort()).start();
            }
        } catch (IOException e) {
            if (running) logger.log(Level.SEVERE, "Ошибка сервера", e);
        }
    }

    public void assignTask(int taskId, double upp, double down, double step) {
        TaskInfo task = new TaskInfo(taskId, upp, down, step);
        
        if (clients.size() >= REQUIRED_CLIENTS) {
            distributeTaskToClients(task);
        } else {
            waitingTasks.add(task);
            logger.warning("Задача #" + taskId + " добавлена в очередь ожидания. Клиентов: " + clients.size() + "/6");
        }
    }

    private void processWaitingTasks() {
        while (!waitingTasks.isEmpty() && clients.size() >= REQUIRED_CLIENTS) {
            TaskInfo task = waitingTasks.poll();
            if (task != null) {
                distributeTaskToClients(task);
            }
        }
    }

    private synchronized void distributeTaskToClients(TaskInfo task) {
        if (clients.size() < REQUIRED_CLIENTS) return;

        double a = task.down;
        double b = task.upp;
        double segmentSize = (b - a) / REQUIRED_CLIENTS;

        partialResultsMap.put(task.taskId, new ConcurrentHashMap<>());

        logger.info("Распределение задачи #" + task.taskId + " [" + a + ", " + b + "] на 6 клиентов...");

        for (int i = 0; i < REQUIRED_CLIENTS; i++) {
            ClientConnection client = clients.get(i);
            
            double subDown = a + i * segmentSize;
            double subUpp = (i == REQUIRED_CLIENTS - 1) ? b : a + (i + 1) * segmentSize;

            client.sendPartialTask(task.taskId, subDown, subUpp, task.step);
        }
    }

    private class ClientConnection implements Runnable {
        private Socket socket;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private volatile boolean busy = false;
        private int clientIndex = -1;

        public ClientConnection(Socket socket) throws IOException {
            this.socket = socket;
            this.socket.setSoTimeout(30000);
            this.oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            this.ois = new ObjectInputStream(socket.getInputStream());
        }

        public void setClientIndex(int index) {
            this.clientIndex = index;
        }

        public boolean isBusy() { return busy; }

        public void sendPartialTask(int taskId, double subDown, double subUpp, double step) {
            try {
                busy = true;
                CalcMessage msg = new CalcMessage(CalcMessage.Type.REQUEST, taskId, subUpp, subDown, step);
                oos.writeObject(msg);
                oos.flush();
                logger.fine("Клиенту #" + clientIndex + " отправлена часть задачи #" + taskId + " [" + subDown + ", " + subUpp + "]");
            } catch (IOException e) {
                logger.log(Level.WARNING, "Ошибка отправки задачи клиенту #" + clientIndex, e);
                close();
            }
        }

        @Override
        public void run() {
            try {
                while (running && !socket.isClosed()) {
                    CalcMessage response;
                    try {
                        response = (CalcMessage) ois.readObject();
                    } catch (SocketTimeoutException e) {
                        continue;
                    } catch (EOFException e) {
                        break;
                    }
                    
                    busy = false;
                    int taskId = response.getTaskId();

                    if (response.getType() == CalcMessage.Type.RESPONSE) {
                        double partialResult = response.getResult();
                        
                        ConcurrentHashMap<Integer, Double> resultsForTask = partialResultsMap.get(taskId);
                        if (resultsForTask != null) {
                            resultsForTask.put(clientIndex, partialResult);
                            
                            if (resultsForTask.size() == REQUIRED_CLIENTS) {
                                double total = 0.0;
                                for (double val : resultsForTask.values()) {
                                    total += val;
                                }
                                
                                partialResultsMap.remove(taskId);
                                
                                final double finalTotal = total;
                                SwingUtilities.invokeLater(() -> {
                                    callback.onTaskCompleted(taskId, finalTotal);
                                });
                                
                                logger.info("Задача #" + taskId + " полностью собрана. Итог: " + finalTotal);
                            }
                        }
                    } else {
                        logger.warning("Ошибка от клиента #" + clientIndex + " для задачи #" + taskId);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                logger.log(Level.INFO, "Клиент #" + clientIndex + " отключился", e);
            } finally {
                close();
            }
        }

        public void close() {
            try {
                busy = false;
                
                if (oos != null) {
                    oos.close();
                }
                
                clients.remove(this);
                
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
                
                logger.info("Клиент удален. Осталось клиентов: " + clients.size());
            } catch (IOException e) {
                logger.log(Level.WARNING, "Ошибка при закрытии клиента", e);
            }
        }
    }

    private static class TaskInfo {
        int taskId;
        double upp, down, step;
        TaskInfo(int taskId, double upp, double down, double step) {
            this.taskId = taskId; this.upp = upp; this.down = down; this.step = step;
        }
    }
}