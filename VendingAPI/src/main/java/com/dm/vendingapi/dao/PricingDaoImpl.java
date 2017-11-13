/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingapi.dao;

import java.util.Map;


/**
 *
 * @author danimaetrix
 */
public class PricingDaoImpl implements PricingDao {


    @Override
    public Map<String,String> loadPricingFromFile(String filename) throws FileIOException {

        FileHandler fileHandler = new FileHandler(filename);
        return fileHandler.readPricingFromFile();

    }

}
