package com.mycompany.mavenproject2;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerManager {
    private static final Logger logger = Logger.getLogger(ServerManager.class.getName());
    private int port;
    private ServerSocket serverSocket;
    private volatile boolean running = false;
    
    // Потокобезопасный список подключённых клиентов
    private final List<ClientConnection> clients = new ArrayList<>();
    // Очередь задач (taskId -> rowIndex в таблице)
    private final ConcurrentLinkedQueue<TaskInfo> pendingTasks = new ConcurrentLinkedQueue<>();
    
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
        logger.info("Сервер запущен на порту " + port);
    }

    public void stop() {
        running = false;
        try { if (serverSocket != null) serverSocket.close(); } catch (IOException e) {}
        clients.forEach(ClientConnection::close);
        clients.clear();
        logger.info("Сервер остановлен");
    }

    private void serverLoop() {
        try {
            serverSocket = new ServerSocket(port);
            while (running) {
                Socket clientSocket = serverSocket.accept();
                //  УСТАНАВЛИВАЕМ ТАЙМАУТ НА СОКЕТ
                clientSocket.setSoTimeout(30000); // 30 секунд
                ClientConnection conn = new ClientConnection(clientSocket);
                clients.add(conn);
                SwingUtilities.invokeLater(() -> 
                    callback.onClientConnected(clientSocket.getInetAddress().getHostAddress()));
                new Thread(conn, "Client-Handler-" + clientSocket.getPort()).start();
            }
        } catch (IOException e) {
            if (running) logger.log(Level.SEVERE, "Ошибка сервера", e);
        }
    }

    // Отправка задачи свободному клиенту
    public void assignTask(int taskId, double upp, double down, double step) {
        pendingTasks.add(new TaskInfo(taskId, upp, down, step));
        distributeTasks();
    }

    private synchronized void distributeTasks() {
        for (ClientConnection client : clients) {
            if (!client.isBusy() && !pendingTasks.isEmpty()) {
                TaskInfo task = pendingTasks.poll();
                client.sendTask(task);
            }
        }
    }

    // Внутренний класс для работы с одним клиентом
    private class ClientConnection implements Runnable {
        private Socket socket;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private volatile boolean busy = false;

        public ClientConnection(Socket socket) throws IOException {
            this.socket = socket;
            //  Таймаут для этого сокета
            this.socket.setSoTimeout(30000);
            
            //  ВАЖНО: сначала создаём OutputStream и ФЛЮШИМ заголовок!
            this.oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush(); // ← КРИТИЧНО: отправляем заголовок потока
            
            // Теперь можно безопасно создавать InputStream
            this.ois = new ObjectInputStream(socket.getInputStream());
        }

        public boolean isBusy() { return busy; }

        public void sendTask(TaskInfo task) {
            try {
                busy = true;
                CalcMessage msg = new CalcMessage(CalcMessage.Type.REQUEST, task.taskId, task.upp, task.down, task.step);
                oos.writeObject(msg);
                oos.flush();
            } catch (IOException e) {
                logger.log(Level.WARNING, "Ошибка отправки задачи", e);
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
                        //  Клиент не отвечает 30 секунд — считаем его отключённым
                        logger.info("Таймаут клиента: " + socket.getInetAddress());
                        break;
                    }
                    
                    busy = false;
                    
                    SwingUtilities.invokeLater(() -> {
                        if (response.getType() == CalcMessage.Type.RESPONSE) {
                            callback.onTaskCompleted(response.getTaskId(), response.getResult());
                        } else {
                            callback.onTaskFailed(response.getTaskId(), response.getErrorMessage());
                        }
                    });
                    
                    // Пробуем выдать следующую задачу этому клиенту
                    SwingUtilities.invokeLater(ServerManager.this::distributeTasks);
                }
            } catch (IOException | ClassNotFoundException e) {
                logger.log(Level.INFO, "Клиент отключился: " + socket.getInetAddress());
            } finally {
                close();
            }
        }

        public void close() {
            try {
                busy = false;
                clients.remove(this);
                if (!socket.isClosed()) socket.close();
            } catch (IOException e) {}
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