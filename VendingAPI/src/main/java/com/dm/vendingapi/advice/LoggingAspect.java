/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingapi.advice;

import com.dm.vendingapi.dao.AuditDao;
import com.dm.vendingapi.dao.FileIOException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 *
 * @author danimaetrix
 */

@Component
@Aspect
public class LoggingAspect {

    AuditDao auditDao;

    @Inject
    public LoggingAspect(AuditDao auditDao) {
        this.auditDao = auditDao;
    }

    @AfterThrowing(
            pointcut = "execution(* com.dm.vendingapi.logic.RealLogic.vendProduct(..))"
            + "|| execution(* com.dm.vendingapi.logic.RealLogic.shakeTheMachine(..))",
            throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        try {
            auditDao.WriteAuditToFile(ex.getMessage());
        } catch (FileIOException e) {
            System.err.println(
                    "ERROR: Could not create audit entry in LoggingAdvice.");
        }
    }

    @Before("execution(* com.dm.vendingapi.logic.RealLogic.shakeTheMachine(..))")
    public void before(JoinPoint joinPoint) {        
        try {
            auditDao.WriteAuditToFile("Customer shook machine!!! ");
        } catch (FileIOException e) {
            System.err.println(
                    "ERROR: Could not create audit entry in LoggingAdvice.");
        }
    }

}
