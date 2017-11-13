/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.advice;

import com.dm.vendingmashine.dao.AuditDao;
import com.dm.vendingmashine.dao.FileIOException;
import org.aspectj.lang.JoinPoint;

/**
 *
 * @author danimaetrix
 */
public class LoggingAdvice {

    AuditDao auditDao;

    public LoggingAdvice(AuditDao auditDao) {
        this.auditDao = auditDao;
    }

    public void writeAuditEntry(JoinPoint jp, Exception ex) {
     
        try {
            auditDao.WriteAuditToFile(ex.getMessage());
        } catch (FileIOException e) {
            System.err.println(
                    "ERROR: Could not create audit entry in LoggingAdvice.");
        }
    }

}
