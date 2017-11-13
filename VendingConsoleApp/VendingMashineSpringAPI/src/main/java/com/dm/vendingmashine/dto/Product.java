/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.dto;

import java.time.LocalDate;

/**
 *
 * @author danimaetrix
 */
public class Product {
    String productname;
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
        return productname;
    }

    public void setProductName(String productname) {
        this.productname = productname;
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

    public void setBestBy(LocalDate bestBy) {
        this.bestBy = bestBy;
    }

}
