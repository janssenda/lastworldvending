/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.controller;

import com.dm.vendingmashine.dao.FileIOException;
import com.dm.vendingmashine.dao.NoItemInventoryException;
import com.dm.vendingmashine.dto.Money;
import com.dm.vendingmashine.logic.*;
import com.dm.vendingmashine.servicelayer.InsufficientFundsException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import com.dm.vendingmashine.servicelayer.VendingService;
import com.dm.vendingmashine.ui.View;

/**
 *
 * @author danimaetrix
 */
public class Controller {

    private final Money userMoney;

    // Dependency Injection
    RealLogic logic;
    View view;

    //service.vtesting ();
    public Controller(View view, VendingService service) {

        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader("logic.cfg")));
            String[] tokens = sc.nextLine().split("[=/]+");
            if (tokens[1].trim().equals("real")){
                this.logic = new RealLogicRealisticImpl(service);
            } else {
                this.logic = new RealLogicIdealImpl(service);
            }

        } catch (FileNotFoundException e) {
            this.logic = new RealLogicIdealImpl(service);
        }


        this.view = view;
        this.userMoney = new Money(BigDecimal.valueOf(0));
    }

    // Main program loop
    public void run() {

        // Make sure that the inventory was loaded correctly
        String errmsg = logic.checkForFileIOErrors();

        if (errmsg != null) {
            view.displayExceptionMessage(errmsg);
        } else {

            // Our main loop - this will continue until user has finished
            drinkMenuSelection(userMoney);

            // Dispense change once loop is complete
            view.showChange(userMoney);

        }

        exitProgram();

    }
    // End main program loop
    // Main drink menu screen

    private void drinkMenuSelection(Money userMoney) {
        boolean repeat = true;
        List<String[]> priceList;

        // Loop the drink menu continuously
        while (repeat) {

            // The price list is obtained with <Sold Out> indicators where necessary
            priceList = logic.returnPriceArrayWithStatus();
            view.cls();

            // We use a generated menu to allow for dynamic changes
            // of inventory and merchandising
            view.generateMenu(priceList, logic.getRealismVersion());

            int choice = view.getUserDrinkSelection(userMoney, priceList.size());

            switch (choice) {
                case 0:
                    // User wants to get chagne and leave
                    return;
                case 1:
                    // User wants to add more money                    
                    view.userAddMoney(userMoney);
                    break;
                case 2:
                    view.showJammedItems(logic.getStuckItems());
                    break;
                case 3:
                    try {
                        view.shakeMachineResults(logic.shakeTheMachine());
                    }catch (DeathException e){
                        view.deathMessage(e.getMessage());
                        userMoney.setTotalmoney(BigDecimal.ZERO);
                    }
                    view.waitOnUser();
                    break;
                default:
                    String name = priceList.get(choice - 4)[0];
                    try {
                        view.showTheProduct(logic.vendProduct(userMoney, name));
                    } catch (NoItemInventoryException e) {
                        view.soldOutBanner();
                    } catch (InsufficientFundsException e) {
                        view.insufficientFundsBanner();
                    } catch (MachineJameException e) {
                        view.machineJamMessage();
                    }

                    view.waitOnUser();
                    break;
            }
        }

    }

    private void exitProgram() {

        // Update the inventory file with changes
        try {
            logic.updateInventory();
        } catch (FileIOException e) {
            view.displayExceptionMessage("Error updating inventory file: " + e.getMessage());
            view.waitOnUser();
        }
        view.showExitMessage();
    }

}
