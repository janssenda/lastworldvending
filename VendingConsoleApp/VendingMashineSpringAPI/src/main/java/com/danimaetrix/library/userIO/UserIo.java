/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danimaetrix.library.userIO;

import java.math.BigDecimal;

/**
 *
 * @author danimaetrix
 */
public interface UserIo {

    public void print(String msg);

    public void print(String msg, String color);

    public void printx(String msg);

    public void printx(String msg, String color);

    public String readLine();

    public String readLine(String msg);

    public String readLine(String msg, String color);

    public String readPasswordLn();

    public String readPasswordLn(String msg);

    public String readPasswordLn(String msg, String color);

    public double readDouble();

    public double readDouble(String msg);

    public double readDouble(String msg, String color);

    public double readDouble(double min, double max);

    public double readDouble(String msg, double min, double max);

    public double readDouble(String msg, double min, double max, String color);

    public int readInt();

    public int readInt(String msg);

    public int readInt(String msg, String color);

    public int readInt(int min, int max);

    public int readInt(String msg, int min, int max);

    public int readInt(String msg, int min, int max, String color);

    public boolean readAnswer();

    public boolean readAnswer(String msg);

    public boolean readAnswer(String msg, String color);

    public BigDecimal readBigDecimal();

    public BigDecimal readBigDecimal(String msg);

    public BigDecimal readBigDecimal(String msg, String color);

    public BigDecimal readBigDecimal(String msg, String min, String max);

    public BigDecimal readBigDecimal(String msg, String min, String max, String color);

}
