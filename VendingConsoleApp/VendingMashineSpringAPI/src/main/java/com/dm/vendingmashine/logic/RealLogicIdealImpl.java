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
import com.dm.vendingmashine.servicelayer.VendingService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author danimaetrix
 */
public class RealLogicIdealImpl implements RealLogic {

    public static final String IMPLEMENTATION = "Ideal";

    VendingService service;

    public RealLogicIdealImpl(VendingService service) {
        this.service = service;
    }

    @Override
    public String getRealismVersion() {
        return IMPLEMENTATION;
    }

    @Override
    public List<String[]> returnPriceArrayWithStatus() {
        return service.returnPriceArrayWithStatus();
    }

    @Override
    public List<Product> vendProduct(Money m, String productName) throws NoItemInventoryException, InsufficientFundsException {
        List<Product> products = new ArrayList<>();
        products.add(service.vendProduct(m, productName));
        return products;
    }

    @Override
    public void updateInventory() throws FileIOException {
        service.updateInventory();
    }

    @Override
    public String checkForFileIOErrors() {
        return service.checkForFileIOErrors();
    }

    @Override
    public Map<String, List<Product>> getStuckItems() {
        Map<String, List<Product>> empty = new HashMap<>();
        return empty;
    }

    @Override
    public List<Product> shakeTheMachine() {
        List<Product> empty = new ArrayList<>();
        return empty;
    }

}
