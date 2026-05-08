package com.mycompany.mavenproject2;

import java.io.Serializable;

/**
 * Сообщение для обмена данными между сервером и клиентом.
 */
public class CalcMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Type { REQUEST, RESPONSE, ERROR }

    private Type type;
    private int taskId;
    private double upp;
    private double down;
    private double step;
    private double result;
    private String errorMessage;

    public CalcMessage(Type type, int taskId, double upp, double down, double step) {
        this.type = type;
        this.taskId = taskId;
        this.upp = upp;
        this.down = down;
        this.step = step;
    }

    public CalcMessage(Type type, int taskId, double result) {
        this.type = type;
        this.taskId = taskId;
        this.result = result;
    }

    public CalcMessage(Type type, int taskId, String errorMessage) {
        this.type = type;
        this.taskId = taskId;
        this.errorMessage = errorMessage;
    }

    public Type getType() { return type; }
    public int getTaskId() { return taskId; }
    public double getUpp() { return upp; }
    public double getDown() { return down; }
    public double getStep() { return step; }
    public double getResult() { return result; }
    public String getErrorMessage() { return errorMessage; }
}