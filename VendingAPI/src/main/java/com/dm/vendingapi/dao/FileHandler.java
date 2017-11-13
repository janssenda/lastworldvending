/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingapi.dao;

import com.dm.vendingapi.dto.Product;

import javax.validation.constraints.Null;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


/* This class allocates reading and writing responsibilities according
to the calling method.  readPricing reads the price map into the system for use
in the service layer.  Inventory IO methods manage importing and exporting of
product list.  The product list is a CSV file containing one line for each single
product (i.e., inventory of 5 coke objects takes 5 lines). Because of this, we can 
allow each individual product object to retain individually owned values. For example,
each can may have a different best - by date, and a different message (as is the norm
in the coca-cola corp currently.  E.g, the names on cans that is part of a marketing campaign.
Now we can surprise the user with these things, and the may be unique per object!!
 */
public class FileHandler {

    private static final String DELIMITER = ",";            // Delimiter for reading and writing files
    private String filename = "";

    // Constructor 
    public FileHandler(String filename) {
        this.filename = filename;
    }

    public Map<String, String> readPricingFromFile() throws FileIOException {
        Scanner scanner;
        Map<String, String> pricing = new LinkedHashMap<>();

        try {
            File f = new File(getClass().getClassLoader().getResource(filename).getFile());
            scanner = new Scanner(new BufferedReader(new FileReader(f)));
        } catch (FileNotFoundException | NullPointerException e) {
            throw new FileIOException("Could not open pricing file. Filename"
                    + " should be 'priceData.csv'", e);
        }

        String currentline;
        String[] currentTokens;

        try {
            while (scanner.hasNextLine()) {

                currentline = scanner.nextLine();
                try {
                    currentTokens = currentline.split(DELIMITER);

                    for (int i = 0; i < currentTokens.length; i++) {
                        currentTokens[i] = currentTokens[i].trim();
                    }

                    if (!currentTokens[0].startsWith("//")) {
                        pricing.put(currentTokens[0], currentTokens[1]);
                    }
                } catch (Exception e) {
                    // Skips the line if there is a problem but continues reading file
                }

            }
        } catch (Exception e) {
            throw new FileIOException("Error reading pricing file: empty or corrupt ", e);
        }

        if (pricing.isEmpty()) {
            throw new FileIOException("Error: no pricing data found.  Please"
                    + " add items to 'priceData.csv'");
        }

        scanner.close();
        return pricing;

    }

    public void writeInventoryToFile(List<Product> ProductList) throws FileIOException {
        PrintWriter out;
        String foutName = filename;

        try {
            File f = new File(getClass().getClassLoader().getResource("output.csv").getFile());
            out = new PrintWriter(new FileWriter(f));
        } catch (IOException | NullPointerException e) {
            throw new FileIOException("Error opening file.  Is it open"
                    + "\nin another application? ", e);
        }

        for (int i = 0; i < ProductList.size(); i++) {

            Product p = ProductList.get(i);
            out.write(p.getProductName() + DELIMITER
                    + p.getBestBy().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + DELIMITER
                    + p.getMessage() + DELIMITER
                    + p.getInformation() + "\n");
        }

        out.flush();
        out.close();
    }

    public List<Product> readInventoryFromFile() throws FileIOException {
        Scanner scanner;

        List<Product> ProductList = new ArrayList<>();



        try {
            File f = new File(getClass().getClassLoader().getResource(filename).getFile());
            scanner = new Scanner(new BufferedReader(new FileReader(f)));
        } catch (FileNotFoundException | NullPointerException e) {
            throw new FileIOException("Could not open inventory file. Filename"
                    + " should be 'inventoryData.csv'", e);
        }

        String currentline;
        String[] currentTokens;

        try {
            while (scanner.hasNextLine()) {

                currentline = scanner.nextLine();
                try {
                    currentTokens = currentline.split(DELIMITER);

                    for (int i = 0; i < currentTokens.length; i++) {
                        currentTokens[i] = currentTokens[i].trim();
                    }

                    if (!currentTokens[0].startsWith("//")) {

                        // We create the physical product and assign it values from our inventoy
                        Product currentProduct = new Product();
                        currentProduct.setProductName(currentTokens[0]);
                        currentProduct.setBestBy(LocalDate.parse(
                                currentTokens[1], DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                        currentProduct.setMessage(currentTokens[2]);
                        currentProduct.setInformation(currentTokens[3]);

                        // Add to our total list.  We bring in ALL items in inventory, so we can sort them
                        ProductList.add(currentProduct);
                    }

                } catch (Exception e) {
                    // Skips the line if there is a problem but continues reading file
                }

            }

        } catch (Exception e) {
            throw new FileIOException("Error reading inventory file: empty or corrupt ", e);
        }

        scanner.close();
        return ProductList;
    }

    public void AuditLogToFile(String entry, boolean writeMode) throws FileIOException {
        PrintWriter out;

        try {
            File f = new File(getClass().getClassLoader().getResource(filename).getFile());
            out = new PrintWriter(new FileWriter(f, writeMode));
        } catch (IOException | NullPointerException e) {
            throw new FileIOException("Error opening audit file! ", e);
        }

        out.write(entry);
        out.write("\r\n");
        out.flush();
        out.close();

    }

}
