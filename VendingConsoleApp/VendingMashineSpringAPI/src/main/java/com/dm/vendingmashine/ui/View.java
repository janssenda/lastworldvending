/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.ui;

import com.dm.vendingmashine.dto.Money;
import com.dm.vendingmashine.dto.Product;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Danimaetrix
 */
public interface View {

    public void showJammedItems(Map<String, List<Product>> itemMap);

    public void showChange(Money userMoney);

    public void generateMenu(List<String[]> pricing, String realVersion);

    // Short method to shorten and extend string to length l, using "..." to truncate 
    // and whitespace to extend.  Used to format fields for output to user in printAllTitles
    public String stShort(String string, int l);

    public String moneyToString(Money userMoney);

    public void showTheProduct(List<Product> productList);

    public Money userAddMoney(Money userMoney);

    public void shakeMachineResults(List<Product> products);
    
    public void machineJamMessage();
            
    public void deathMessage(String e);

    public int getUserDrinkSelection(Money userMoney, int range);

    public void userAddMoneyBanner();

    public void insufficientFundsBanner();

    public void soldOutBanner();

    public void drinkSelectionBanner();

    // Additional user messages
    public void showExitMessage();

    // Pause for user
    public void waitOnUser();

    public void cls();

    public void displayExceptionMessage(String msg);

}
