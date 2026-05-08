package com.mycompany.mavenproject2;

import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 * Приложение-клиент.
 * Подключается к серверу, получает часть интервала для вычисления,
 * выполняет расчёт в 6 внутренних потоках и возвращает результат.
 */
public class ClientApp {
    private static final Logger logger = Logger.getLogger(ClientApp.class.getName());
    private static final String SERVER_IP = "192.168.0.111"; 
    private static final int SERVER_PORT = 8888;

    public static void main(String[] args) {
        logger.info("Запуск клиента... Ожидание подключения к серверу.");
        
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            //socket.setSoTimeout(60000); 
            
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(socket.getInputStream());
            
            logger.info("Подключено к серверу " + SERVER_IP + ":" + SERVER_PORT);
            logger.info("Клиент готов к работе. Будет работать, пока сервер не отключится.");
            
            while (!socket.isClosed()) {
                CalcMessage request = null;
                
                try {
                    request = (CalcMessage) ois.readObject();
                } catch (EOFException e) {
                    logger.info("Сервер закрыл соединение. Клиент завершает работу.");
                    break;
                } catch (ClassNotFoundException e) {
                    logger.log(Level.SEVERE, "Ошибка десериализации объекта", e);
                    continue;
                }

                if (request != null && request.getType() == CalcMessage.Type.REQUEST) {
                    logger.info("Получена часть задачи #" + request.getTaskId() + 
                               " | Мой отрезок: [" + request.getDown() + ", " + request.getUpp() + "]");
                    
                    try {
                        RecIntegral rec = new RecIntegral(request.getUpp(), request.getDown(), request.getStep());
                        
                        double partialResult = rec.calculateIntegralParallel6Threads();
                        
                        CalcMessage response = new CalcMessage(CalcMessage.Type.RESPONSE, request.getTaskId(), partialResult);
                        oos.writeObject(response);
                        oos.flush();
                        
                        logger.info("Частичный результат для задачи #" + request.getTaskId() + ": " + partialResult);
                        
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "Ошибка вычисления части задачи #" + request.getTaskId(), e);
                        CalcMessage err = new CalcMessage(CalcMessage.Type.ERROR, request.getTaskId(), e.getMessage());
                        oos.writeObject(err);
                        oos.flush();
                    }
                }
            }
            
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Критическая ошибка сети. Клиент завершает работу.", e);
        } finally {
            try {
                if (oos != null) oos.close();
                if (ois != null) ois.close();
                if (socket != null && !socket.isClosed()) socket.close();
            } catch (IOException ex) {
            }
            logger.info("Клиент полностью остановлен.");
        }
    }
}