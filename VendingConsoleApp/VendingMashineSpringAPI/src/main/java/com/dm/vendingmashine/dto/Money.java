/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author danimaetrix
 */
public class Money {

    BigDecimal quarters, dimes, nickels, pennies;
    BigDecimal totalmoney;

    public Money(BigDecimal totalmoney) {
        this.totalmoney = totalmoney;
        breakMoney();
    }

    public void setQuarters(BigDecimal quarters) {
        this.quarters = quarters;
    }

    public void setDimes(BigDecimal dimes) {
        this.dimes = dimes;
    }

    public void setNickels(BigDecimal nickels) {
        this.nickels = nickels;
    }

    public void setPennies(BigDecimal pennies) {
        this.pennies = pennies;
    }

    public void setTotalmoney(BigDecimal totalmoney) {
        this.totalmoney = totalmoney;
        breakMoney();
    }

    // Break the total amount into real USD.  Note
    // rounding up takes place in this process to
    //facilitate conversion to real values.
    public void breakMoney() {
        BigDecimal r = totalmoney.movePointRight(0);

        BigDecimal[] result = r.divideAndRemainder(BigDecimal.valueOf(0.25));
        setQuarters(result[0]);

        result = result[1].divideAndRemainder(BigDecimal.valueOf(0.10));
        setDimes(result[0]);

        result = result[1].divideAndRemainder(BigDecimal.valueOf(0.05));
        setNickels(result[0]);

        result = result[1].divideAndRemainder(BigDecimal.valueOf(0.01));
        setPennies(result[0].setScale(0, RoundingMode.HALF_UP));

    }   

    public String getQuarters() {
        return quarters.setScale(0, RoundingMode.HALF_UP).toString();
    }

    public String getDimes() {
        return dimes.setScale(0, RoundingMode.HALF_UP).toString();
    }

    public String getNickels() {
        return nickels.setScale(0, RoundingMode.HALF_UP).toString();
    }

    public String getPennies() {
        return pennies.setScale(0, RoundingMode.HALF_UP).toString();
    }

    public BigDecimal getTotalmoney() {
        return totalmoney;
    }

}
