package com.mycompany.mavenproject2;

import java.io.*;
import java.net.*;
import java.util.logging.*;

public class ClientApp {
    private static final Logger logger = Logger.getLogger(ClientApp.class.getName());
    private static final String SERVER_IP = "192.168.10.65"; // Настройте под ваш сервер
    private static final int SERVER_PORT = 8888;

    public static void main(String[] args) {
        logger.info("Запуск клиента...");
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            // УСТАНАВЛИВАЕМ ТАЙМАУТ
            socket.setSoTimeout(30000);
            
            // Сначала создаём OutputStream и ФЛЮШИМ заголовок!
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush(); // ← КРИТИЧНО: отправляем заголовок потока
            
            // Теперь безопасно создаём InputStream
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            
            logger.info("Подключено к серверу " + SERVER_IP + ":" + SERVER_PORT);
            
            while (!socket.isClosed()) {
                CalcMessage request;
                try {
                    request = (CalcMessage) ois.readObject();
                } catch (SocketTimeoutException e) {
                    logger.info("Таймаут ожидания задачи, продолжаю ждать...");
                    continue;
                } catch (EOFException e) {
                    logger.info("Сервер закрыл соединение");
                    break;
                }
                
                if (request.getType() == CalcMessage.Type.REQUEST) {
                    logger.info("Получена задача #" + request.getTaskId() + 
                               " | [" + request.getDown() + ", " + request.getUpp() + "] шаг=" + request.getStep());
                    
                    try {
                        RecIntegral rec = new RecIntegral(request.getUpp(), request.getDown(), request.getStep());
                        // === ВЫЧИСЛЕНИЕ В 6 ПОТОКАХ (как в предыдущей работе) ===
                        double result = rec.calculateIntegralParallel6Threads();
                        
                        CalcMessage response = new CalcMessage(CalcMessage.Type.RESPONSE, request.getTaskId(), result);
                        oos.writeObject(response);
                        oos.flush();
                        
                        logger.info("Задача #" + request.getTaskId() + " выполнена. Результат: " + result);
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "Ошибка вычисления задачи #" + request.getTaskId(), e);
                        CalcMessage err = new CalcMessage(CalcMessage.Type.ERROR, request.getTaskId(), e.getMessage());
                        oos.writeObject(err);
                        oos.flush();
                    }
                }
            }
            
            // Закрываем ресурсы
            ois.close();
            oos.close();
            socket.close();
            
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Клиент завершил работу", e);
        }
    }
}