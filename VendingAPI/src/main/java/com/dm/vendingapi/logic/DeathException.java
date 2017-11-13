/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingapi.logic;

/**
 *
 * @author Danimaetrix
 */
public class DeathException extends Exception{

    public DeathException(String message) {
        super(message);
    }

    public DeathException(String message, Throwable cause) {
        super(message, cause);
    }

}
