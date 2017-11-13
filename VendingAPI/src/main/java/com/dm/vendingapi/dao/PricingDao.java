package com.dm.vendingapi.dao;

import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author danimaetrix
 */
public interface PricingDao {

    public Map<String, String> loadPricingFromFile(String filename) throws FileIOException;

}
