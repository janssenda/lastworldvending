/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingapi.logic;

/**
 *
 * @author danimaetrix
 */
public class MachineJameException extends Exception {

    public MachineJameException(String message) {
        super(message);
    }

    public MachineJameException(String message, Throwable cause) {
        super(message, cause);
    }

}
