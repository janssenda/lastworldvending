/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingapi.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author danimaetrix
 */
public class Product {
    String productName;
    LocalDate bestBy;
    String message;
    String information;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public LocalDate getBestBy() {
        return bestBy;
    }

    public String getBestByStr() {
        return bestBy.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    public void setBestBy(LocalDate bestBy) {
        this.bestBy = bestBy;
    }

}
