/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.dao;

/**
 *
 * @author danimaetrix
 */
public interface AuditDao {
    
    public void WriteAuditToFile(String entry) throws FileIOException;
    
}
