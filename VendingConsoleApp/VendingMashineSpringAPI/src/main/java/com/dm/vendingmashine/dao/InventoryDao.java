/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.dao;

import com.dm.vendingmashine.dto.Product;
import java.util.List;
import java.util.Map;

/**
 *
 * @author danimaetrix
 */
public interface InventoryDao {
    
    public void setInventory(Map<String, List<Product>> inventory);

    // Used to read the inventory from file.  Inventory is stored as a list of all the objects 
    // currently in the machine
    public Map<String, List<Product>> readInventoryFromFile(String filename) throws FileIOException;

    // Writes the inventory back to file when program is closed.  This can be understood
    // as updating the inventory after the user is finished.
    public void writeInventoryToFile(String filename) throws FileIOException;

    // Our vend method.  This method gets the next-in-line product of the requested 
    // productname. It returns the object to be "vended" upward to the user, and 
    // removes it from the corresponding list.  Our items will be nicely vended
    // in the order in which they have been loaded into the machine!!    
    public Product vendItem(String productName) throws NoItemInventoryException;

    // Simply obtains the inventory of a product name by getting the length of
    // the ArrayList mapped to by that name.
    public int getProductQuantity(String productName);

    public Product getProduct(String productName);    

}
