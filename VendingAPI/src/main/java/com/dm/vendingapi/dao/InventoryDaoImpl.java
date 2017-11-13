/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingapi.dao;


import com.dm.vendingapi.dto.Product;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author danimaetrix
 */
public class InventoryDaoImpl implements InventoryDao {

    private Map<String, List<Product>> inventory = new HashMap<>();

    @Override
    public void setInventory(Map<String, List<Product>> inventory) {
        this.inventory = inventory;
    }

    @Override
    public Map<String, List<Product>> readInventoryFromFile(String filename) throws FileIOException {

        // Instantiate our handler and get the total list of products from the file
        FileHandler fileHandler = new FileHandler(filename);
        List<Product> InventoryList = fileHandler.readInventoryFromFile();

        // Stream the list and sort into our map. NOTE: Our items can now be out of order and will still sort!!! :) :)
        //this.inventory = InventoryList.stream().collect(Collectors.groupingBy(p -> p.getProductName()));
        this.inventory = InventoryList.stream().collect(Collectors.groupingBy(Product::getProductName));

        return this.inventory;
    }

    @Override
    public void writeInventoryToFile(String filename) throws FileIOException {
        // Instantiate our handler and get the being a list of products for the file
        FileHandler fileHandler = new FileHandler(filename);
        List<Product> ProductList = new ArrayList<>();

        // We need to reverse the process of bringing the data in.  We obtain 
        // the keyset from the inventory map, and stream the keys.
        // for each key name, we obtain a list containing product objects associated
        // with that key.  We then simply append the list to our master list and move
        // to the next key.  In this way we can have dynamically changing inventory
        // without the need for explicit management.  Our sorting algorithm organizes
        // incoming data, so we do not care how it is laid out here: only that it gets
        // written as a list with the correct formatting!!!
        Set<String> brands = inventory.keySet();
        brands.stream().forEach(name -> {
            List<Product> l = (ArrayList<Product>) inventory.get(name);
            ProductList.addAll(l);
        });

        fileHandler.writeInventoryToFile(ProductList);

    }

    // Our vend method.  This method gets the next-in-line product of the requested 
    // productname. It returns the object to be "vended" upward to the user, and 
    // removes it from the corresponding list.  Our items will be nicely vended
    // in the order in which they have been loaded into the machine!!
    @Override
    public Product vendItem(String productName) throws NoItemInventoryException {

        if (getProductQuantity(productName) == 0) {
            throw new NoItemInventoryException(productName + ": Item not in inventory... ");
        } else {
            Product p = inventory.get(productName).get(0);
            inventory.get(productName).remove(0);

            return p;
        }
    }

    // Simply obtains the inventory of a product name by getting the length of
    // the ArrayList mapped to by that name. If the key does not exist, returns 0
    // to avoid null pointers and allow main menu to display sold out staus properly
    @Override
    public int getProductQuantity(String productName) {
        //System.out.println(productName);
        if (inventory.containsKey(productName)) {
            return inventory.get(productName).size();
        } else {
            return 0;
        }
    }

    @Override
    public Product getProduct(String productName) {
        return inventory.get(productName).get(0);
    }

}
