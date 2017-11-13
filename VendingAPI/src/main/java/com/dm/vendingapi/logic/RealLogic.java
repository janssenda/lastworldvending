/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingapi.logic;



import com.dm.vendingapi.dao.FileIOException;
import com.dm.vendingapi.dao.NoItemInventoryException;
import com.dm.vendingapi.dto.Money;
import com.dm.vendingapi.dto.Product;
import com.dm.vendingapi.dto.ProductMessenger;
import com.dm.vendingapi.servicelayer.InsufficientFundsException;

import java.util.List;
import java.util.Map;

/**
 *
 * @author danimaetrix
 */
public interface RealLogic {

    public List<ProductMessenger> returnInventoryMap();

    public String getProductPrice(String productName);

    public String getRealismVersion();

    public List<Product> shakeTheMachine() throws DeathException;

    public List<String[]> returnPriceArrayWithStatus();

    public List<Product> vendProduct(Money m, String productName)
            throws NoItemInventoryException,
            InsufficientFundsException,
            MachineJameException;

    public void updateInventory() throws FileIOException;

    public String checkForFileIOErrors();

    public Map<String, List<Product>> getStuckItems();

    public void setRealism(boolean realism);

    public boolean getRealism();
}
