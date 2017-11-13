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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author danimaetrix
 */
public class RealLogicRealisticImpl implements RealLogic {

    private static final String IMPLEMENTATION = "Realistic v1.0";
    private VendingService service;
    private Map<String, List<Product>> itemMap;

    public RealLogicRealisticImpl(VendingService service) {
        this.service = service;
        this.itemMap = new LinkedHashMap<>();
    }

    // Returns the version of this implementation
    @Override
    public String getRealismVersion() {
        return IMPLEMENTATION;
    }

    // Hook into the vend product method.  Returns a list of products
    // instead of a single product -- this allows for multiple items to 
    // be vended at once - i.e., when an item is jammed, and the user
    // buys a second one in order to displace the first.
    @Override
    public List<Product> vendProduct(Money m, String productName)
            throws NoItemInventoryException,
            InsufficientFundsException,
            MachineJameException {

        Product p = service.vendProduct(m, productName);
        List<Product> products = determineVendedProduct(p);

        return products;
    }

    // Allows the user to shake the machine.  There are several possible
    // non deterministic outcomes to this action.  There is a chance that
    // the machine can tip over and kill the user.  If that does not 
    // happen, then the method goes through the map of jammed objects,
    // and determines whether the front row of each falls down.  
    // Unjammed items are not affected by this action.  The method
    // returns all sucesfully released items, or the empty list
    // if none are released.
    
    // The chances of the above outcomes are determined via sampling
    // of a Gaussian distributed random variable. The probability of the 
    // events can be set by in the methods, and are labeled as the threshold
    // values.  They are mapped to the standard normal distribution Z-tables
    // shown below.  The chance of death, for example, is set to 1.0, which
    // corresponds to a probability of 16% according to the table below.
    
    // Threshold:   0.0 0.20 0.40 0.50 0.60 0.80 1.00
    // Probability: 0.5 0.42 0.34 0.31 0.27 0.21 0.16
    
    // Threshold:   1.20 1.40 1.50 1.60 1.80 2.00
    // Probability: 0.12 0.08 0.07 0.05 0.04 0.02    

    @Override
    public List<Product> shakeTheMachine() throws DeathException {
        List<Product> products = new ArrayList<>();
        List<Product> tempList;
        List<String> toProcess = new ArrayList<>();
        Random rand = new Random();
        double deathThreshold = 1, releaseThreshold = 0.5;

        if (Math.abs(rand.nextGaussian()) > deathThreshold) {
            throw new DeathException("Customer killed by machine... ");
        }

        // Checks wether each item falls
        for (String key : itemMap.keySet()) {
            if (Math.abs(rand.nextGaussian()) > releaseThreshold) {
                tempList = itemMap.get(key);
                products.add(tempList.get(0));
                toProcess.add(key);
            }
        }

        // Process marked items.  Done in a separate loop to avoid
        // concurrency exceptions in the foreach loop.
        for (int i = 0; i < toProcess.size(); i++) {

            tempList = itemMap.get(toProcess.get(i));
            tempList.remove(0);

            if (tempList.isEmpty()) {
                itemMap.remove(toProcess.get(i));
            } else {
                itemMap.put(toProcess.get(i), tempList);
            }
        }

        return products;
    }

    // Lets us see what items are currently jammed
    @Override
    public Map<String, List<Product>> getStuckItems() {
        return itemMap;
    }

    // Implements the logic surrounding vending event.  If an item jams, 
    // it is automatically stored in the itemMap, which tracks all 
    // currently jammed items *in the order they were jammed*.  This means
    // that items which jammed first will autopmatically be in the front of each
    // list in the map.  This adds realism to the simulation - remember that
    // each item is unique and contains unique properties in this model, and 
    // therefore the order of mapped data *is* important.
    private List<Product> determineVendedProduct(Product p) throws MachineJameException {
        List<Product> products = new ArrayList<>();
        String productName = p.getProductName();
        try {
            machineJamStatus();
            if (itemMap.containsKey(productName)) {
                products = itemMap.get(productName);
                itemMap.remove(productName);
            }
            products.add(p);
        } catch (MachineJameException e) {
            List<Product> productbuffer = new ArrayList<>();
            if (itemMap.containsKey(productName)) {
                productbuffer = itemMap.get(productName);
            }
            productbuffer.add(p);
            itemMap.put(productName, productbuffer);

            throw e;
        }
        return products;
    }

    // Uses the random gaussian method described above to decide
    // whether the machine jams.  The exception thrown by this method
    // is logged via AOP and includes the number of items currently jammed.
    private void machineJamStatus() throws MachineJameException {
        Random rand = new Random();
        long[] jammedItems = {1};
        double jamThreshold = 0.7;        

        if (Math.abs(rand.nextGaussian()) > jamThreshold) {
            itemMap.forEach((k, v) -> {
                jammedItems[0] = jammedItems[0] + v.size();
            });
            
            throw new MachineJameException("Machine Jammed, "
                    + jammedItems[0] + " currently stuck...");
        }
    }

    // Below are pass-through methods
    @Override
    public List<String[]> returnPriceArrayWithStatus() {
        return service.returnPriceArrayWithStatus();
    }

    @Override
    public void updateInventory() throws FileIOException {
        service.updateInventory();
    }

    @Override
    public String checkForFileIOErrors() {
        return service.checkForFileIOErrors();
    }

}
