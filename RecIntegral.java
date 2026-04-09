package com.mycompany.mavenproject2;
import java.io.Serializable;

/**
 * Класс, описывающий одну запись для расчёта интеграла
 * @author 1
 */
public class RecIntegral implements Serializable {
    private static final long serialVersionUID = 1L;
    private Double upp;      // Верхний предел
    private Double down;     // Нижний предел
    private Double step;     // Шаг интегрирования
    private Double res;      // Результат вычисления

    // Конструктор по умолчанию
    public RecIntegral() {}

    // Конструктор с параметрами и проверкой
    public RecIntegral(Double upp, Double down, Double step) throws RecException, RecException2, RecException3 {
        check(upp, "Верхний порог");
        check(down, "Нижний порог");
        check(step, "Шаг");
        checkInterval(upp, down, step);
        checkOrder(upp, down);
        this.upp = upp;
        this.down = down;
        this.step = step;
        this.res = null;
    }

    // === Методы проверки ===
    private static void check(double value, String paramName) throws RecException {
        if (value < 0.000001 || value > 1000000) {
            throw new RecException(paramName + " вне диапазона!", value);
        }
    }
    
    private static void checkInterval(double upp, double down, double step) throws RecException2 {
        if (upp - down < step) {
            throw new RecException2("Шаг больше интервала!", step);
        }
    }
    
    private static void checkOrder(double upp, double down) throws RecException3 {
        if (down > upp) {
            throw new RecException3("Верхний диапазон меньше нижнего!", upp, down);
        }
    }

    // === Однопоточный расчёт (метод трапеций) ===
    public double calculateIntegralTrapezoidal() {
        if (step == null || upp == null || down == null || step <= 0 || down >= upp) {
            return 0.0;
        }
        return calculateTrapezoidalSegment(down, upp, step);
    }

    // === Многопоточный расчёт: 6 потоков через Runnable ===
    public double calculateIntegralParallel6Threads() {
        if (step == null || upp == null || down == null || step <= 0 || down >= upp) {
            return 0.0;
        }

        double a = down;
        double b = upp;
        double h = step;
        final int THREAD_COUNT = 6;
        
        // Разбиваем интервал на 6 частей
        double segmentSize = (b - a) / THREAD_COUNT;
        
        IntegralTask[] tasks = new IntegralTask[THREAD_COUNT];
        Thread[] threads = new Thread[THREAD_COUNT];

        // Создаём и запускаем потоки
        for (int i = 0; i < THREAD_COUNT; i++) {
            double start = a + i * segmentSize;
            double end = (i == THREAD_COUNT - 1) ? b : a + (i + 1) * segmentSize;
            
            tasks[i] = new IntegralTask(start, end, h);
            threads[i] = new Thread(tasks[i], "Integral-Thread-" + i);
            threads[i].start();
        }

        // Ждём завершения всех потоков и суммируем результаты
        double totalResult = 0.0;
        for (int i = 0; i < THREAD_COUNT; i++) {
            try {
                threads[i].join();
                totalResult += tasks[i].getResult();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(java.util.logging.Level.SEVERE, "Поток прерван", e);
                return 0.0;
            }
        }
        return totalResult;
    }

    // Вспомогательный метод: расчёт интеграла на отрезке [start, end] методом трапеций
    private static double calculateTrapezoidalSegment(double start, double end, double step) {
        double a = start;
        double h = step;
        double sum = 0.0;
        
        while (a + h <= end) {
            double y1 = Math.sqrt(a);
            double y2 = Math.sqrt(a + h);
            sum += h * (y1 + y2) / 2.0;
            a += h;
        }
        
        // Обрабатываем остаток, если конец интервала не кратен шагу
        if (a < end) {
            double remainder = end - a;
            double y1 = Math.sqrt(a);
            double y2 = Math.sqrt(end);
            sum += remainder * (y1 + y2) / 2.0;
        }
        return sum;
    }

    // === Внутренний класс задачи для потока ===
    private static class IntegralTask implements Runnable {
        private final double start;
        private final double end;
        private final double step;
        private double result;

        public IntegralTask(double start, double end, double step) {
            this.start = start;
            this.end = end;
            this.step = step;
            this.result = 0.0;
        }

        @Override
        public void run() {
            result = calculateTrapezoidalSegment(start, end, step);
        }

        public double getResult() {
            return result;
        }
    }

    // === Геттеры и сеттеры ===
    public Double getUpp() { return upp; }
    public void setUpp(Double upp) throws RecException {
        check(upp, "Верхний порог");
        this.upp = upp;
    }

    public Double getDown() { return down; }
    public void setDown(Double down) throws RecException {
        check(down, "Нижний порог");
        this.down = down;
    }

    public Double getStep() { return step; }
    public void setStep(Double step) throws RecException {
        check(step, "Шаг");
        this.step = step;
    }

    public Double getRes() { return res; }
    public void setRes(Double res) { this.res = res; }
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(RecIntegral.class.getName());
}