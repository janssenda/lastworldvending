/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.logic;

import com.dm.vendingmashine.dao.FileIOException;
import com.dm.vendingmashine.dao.NoItemInventoryException;
import com.dm.vendingmashine.dto.Money;
import com.dm.vendingmashine.dto.Product;
import com.dm.vendingmashine.servicelayer.InsufficientFundsException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author danimaetrix
 */
public interface RealLogic {

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
}
