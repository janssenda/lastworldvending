/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.servicelayer;

import com.dm.vendingmashine.dao.FileIOException;
import com.dm.vendingmashine.dao.NoItemInventoryException;
import com.dm.vendingmashine.dto.Money;
import com.dm.vendingmashine.dto.Product;
import java.util.List;
import java.util.Map;

/**
 *
 * @author danimaetrix
 */
public interface VendingService {
    
    public Map<String, String> getPricing();
    public void setPricing(Map<String, String> pricing);
    
    public Map<String, String> returnPriceMap();

    // Builds the arraylist for dynamic main menu.  Must be located in service layer
    // because it needs access to BOTH dao implementations
    public List<String[]> returnPriceArrayWithStatus();

    public Product getProduct(String productName);

    public Product vendProduct(Money m, String productName)throws NoItemInventoryException, InsufficientFundsException ;

    // Simply queries the dao to check on item availability.  Returns true if sold out.
    public boolean isSoldOut(String productName);

    // We compare the big decimal values of the money object and the price
    // and return true if the money is >= the price, and false otherwise
    public boolean validateMoney(Money m, String productName) throws InsufficientFundsException;

    // Computes the change for user.  The change in available funds is computed via
    // big decimal methods to retain accuracy.  The physical change, however, must be rounded
    // to two decimal places so it can be broken down to US denominations evenly.
    // Since prices and paymnent are in the form of two decmial place doubles anyways,
    // the "rounding" that takes place here actually does nothing but is included
    // for completeness (i.e., we could charge fractional cents on top of items if we 
    // really wanted to, without requiring any code modification.    
    public Money calculateChange(Money m, String name);


    public void updateInventory() throws FileIOException;
    
    public String checkForFileIOErrors();
    
    }
