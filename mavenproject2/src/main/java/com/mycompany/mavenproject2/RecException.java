/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author 1
 */
public class RecException extends Exception{
    private double value;
    public double getNumber(){return value;}
    public RecException(String message, double num){
        super(message);
        value = num;
    }

}
