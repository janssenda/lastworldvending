/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.app;

import com.dm.vendingmashine.controller.Controller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author danimaetrix
 */
public class App {

    public static void main(String[] args)  {
        

        ApplicationContext ctx = 
                new ClassPathXmlApplicationContext("applicationContext.xml");
        
        
        Controller controller = 
                ctx.getBean("controller", Controller.class);
        
   
        controller.run();



    }
}
