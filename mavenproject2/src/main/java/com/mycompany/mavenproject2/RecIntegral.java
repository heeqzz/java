/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;
import java.io.Serializable;
        
/**
 * Класс, описывающий одну запись для расчёта интеграла
 * @author 1
 */
public class RecIntegral implements Serializable {
    private static final long serialVersionUID = 1;
    private Double upp;      // Верхний предел
    private Double down;     // Нижний предел
    private Double step;     // Шаг интегрирования
    private Double res;      // Результат вычисления

    // Конструктор по умолчанию
    public RecIntegral() {
    }

    // Конструктор с параметрами и проверкой
    public RecIntegral(Double upp, Double down, Double step) throws RecException, RecException2,RecException3 {
        check(upp, "Верхний порог");
        check(down, "Нижний порог");
        check(step, "Шаг");
        check(upp,down,step);
        check(upp,down);
        this.upp = upp;
        this.down = down;
        this.step = step;
        this.res = null;
    }

    // Метод проверки значения
    private static void check(double value, String paramName) throws RecException {
        if (value < 0.000001 || value > 1000000) {
            throw new RecException(paramName + " вне диапазона!", value);
        }
        
    }
    private static void check(double upp,double down,double step) throws RecException2 {
        if (upp-down<step) {
            throw new RecException2("Шаг больше интервала!",step);
        }
    }
    private static void check(double upp,double down) throws RecException3 {
        if (down>upp) {
            throw new RecException3(" Верхний диапазон меньше нижнего!", upp,down);
        }
        
    }
    // Публичный метод расчёта интеграла методом трапеций
    public double calculateIntegralTrapezoidal() {
        if (step == null || upp == null || down == null || step <= 0 || down >= upp) {
            return 0.0;
        }
        
        double a = down;
        double b = upp;
        double h = step;
        double sum = 0.0;
        
        while (a + h <= b) {
            double y1 = Math.sqrt(a);
            double y2 = Math.sqrt(a + h);
            sum += h * (y1 + y2) / 2.0;
            a += h;
        }
        
        if (a < b) {
            double remainder = b - a;
            double y1 = Math.sqrt(a);
            double y2 = Math.sqrt(b);
            sum += remainder * (y1 + y2) / 2.0;
        }
        return sum;
    }
  
    public Double getUpp() {
        return upp;
    }

    public void setUpp(Double upp) throws RecException {
        check(upp, "Верхний порог");
        this.upp = upp;
    }

    public Double getDown() {
        return down;
    }

    public void setDown(Double down) throws RecException {
        check(down, "Нижний порог");
        this.down = down;
    }

    public Double getStep() {
        return step;
    }

    public void setStep(Double step) throws RecException {
        check(step, "Шаг");
        this.step = step;
    }

    public Double getRes() {
        return res;
    }

    public void setRes(Double res) {
        this.res = res;
    }
    
}