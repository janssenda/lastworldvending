/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.dto;

import java.math.BigDecimal;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author danimaetrix
 */
public class MoneyTest {

    public MoneyTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testBreakMoney() {
        Money m = new Money(new BigDecimal("2.50"));


        assertEquals("10", m.getQuarters());
        assertEquals("0", m.getNickels());
        assertEquals("0", m.getDimes());
        assertEquals("0", m.getPennies());

    }

    @Test
    public void testBreakMoney2() {
        Money m = new Money(new BigDecimal("2.87"));

        assertEquals("11", m.getQuarters());
        assertEquals("1", m.getDimes());
        assertEquals("0", m.getNickels());
        assertEquals("2", m.getPennies());

    }

    @Test
    public void testBreakMoney3() {
        Money m = new Money(new BigDecimal("0.58"));

        assertEquals("2", m.getQuarters());
        assertEquals("1", m.getNickels());
        assertEquals("0", m.getDimes());
        assertEquals("3", m.getPennies());


    }

}
