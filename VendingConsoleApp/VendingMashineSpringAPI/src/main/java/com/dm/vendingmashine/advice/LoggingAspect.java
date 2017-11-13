/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.advice;

/**
 *
 * @author danimaetrix
 */
import com.dm.vendingmashine.dao.AuditDao;
import com.dm.vendingmashine.dao.FileIOException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoggingAspect {

    AuditDao auditDao;

    public LoggingAspect(AuditDao auditDao) {
        this.auditDao = auditDao;
    }

    @AfterThrowing(
            pointcut = "execution(* com.dm.vendingmashine.logic.RealLogic.vendProduct(..))"
            + "|| execution(* com.dm.vendingmashine.logic.RealLogic.shakeTheMachine(..))",
            throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        //Object[] args = joinPoint.getArgs();
        try {
            auditDao.WriteAuditToFile(ex.getMessage());
        } catch (FileIOException e) {
            System.err.println(
                    "ERROR: Could not create audit entry in LoggingAdvice.");
        }
    }

    @Before("execution(* com.dm.vendingmashine.logic.RealLogic.shakeTheMachine(..))")
    public void before(JoinPoint joinPoint) {        
        try {
            auditDao.WriteAuditToFile("Customer shook machine!!! ");
        } catch (FileIOException e) {
            System.err.println(
                    "ERROR: Could not create audit entry in LoggingAdvice.");
        }
    }

}
