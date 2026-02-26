/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;
import java.util.LinkedList;
/**
 *
 * @author 1
 */
public class RecIntegral {
    public Double upp;
    public Double down;
    public Double step;
    
    // Статическая коллекция для хранения всех записей
    private static LinkedList<RecIntegral> collection = new LinkedList<>();
    
    // Конструктор: создаёт запись и сразу добавляет в коллекцию
    // Метод проверки одного числа
    private static void check(double value, String paramName) throws RecException {
        if ((value < 0.000001) || (value > 1000000)) {
            throw new RecException(paramName + " вне диапазона!", value);
        }
    }
    
    // Конструктор с проверкой всех трёх чисел
    public RecIntegral(Double upp, Double down, Double step) throws RecException {
        check(upp, "Верхний порог");    // проверка upp
        check(down, "Нижний порог");    // проверка down
        check(step, "Шаг");             // проверка step
        
        this.upp = upp;
        this.down = down;
        this.step = step;
        collection.add(this);
    }
    
    // Метод для доступа к коллекции
    public static LinkedList<RecIntegral> GetCollection() {
        return collection;
    }

    private void and(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
