/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author student
 */
public class RecException3 extends Exception{
    private double value;
    private double value2;
    public double getNumber(){return value;}
    public double getNumber2(){return value2;}
    public RecException3(String message, double num,double num2){
        super(message);
        value = num;
        value2=num2;
    }
}
