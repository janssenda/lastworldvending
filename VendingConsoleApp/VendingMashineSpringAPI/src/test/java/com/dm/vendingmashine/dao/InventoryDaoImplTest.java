/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.dao;

import com.dm.vendingmashine.dto.Product;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Danimaetrix
 */
public class InventoryDaoImplTest {

//    InventoryDaoStubImpl dao = new InventoryDaoStubImpl();
    InventoryDao dao;

    public InventoryDaoImplTest() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        this.dao = ctx.getBean("inventoryDao", InventoryDaoImpl.class);        
    }

    @Before
    public void setUp() {
        Map<String, List<Product>> inventory = readDummyInventory();
        dao.setInventory(inventory);
    }

    @Test
    public void testReadInventoryFromFile() throws Exception {
    }

    @Test
    public void testWriteInventoryToFile() throws Exception {
    }

    @Test
    public void testVendItem() {
        // Verifies that items are vended in the correct order
        // no inventory test is below  
       
        try {
            Product testP = dao.getProduct("Coke");
            Product testP2 = dao.vendItem("Coke");

            assertEquals(testP, testP2);

            testP = dao.getProduct("Coke");
            testP2 = dao.vendItem("Coke");

            assertEquals(testP, testP2);
        } catch (NoItemInventoryException e) {

        }

    }

    @Test
    public void testGetProductQuantity_testNoItemException() {
        // We check our quantities initially, and then vend
        // each item.  We check each time to make sure that
        // The next one in line is the next one to be vended.
        // Once we reach zero, we test our exception by trying
        // to vend one more.

        assertEquals(3, dao.getProductQuantity("Coke"));

        // Vend some items to reduce quantity and recheck each time
        try {
            dao.vendItem("Coke");
            assertEquals(2, dao.getProductQuantity("Coke"));

            dao.vendItem("Coke");
            assertEquals(1, dao.getProductQuantity("Coke"));

            dao.vendItem("Coke");
            assertEquals(0, dao.getProductQuantity("Coke"));
        } catch (NoItemInventoryException e) {
            // these are fine as inventory is > 0
        }

        // Make another attempt to vend item once quantity is zero....
        try {
            dao.vendItem("Coke");
            fail("Exception was not caught...");
        } catch (NoItemInventoryException e) {
            // Exception caught appropriately
        }

    }

    @Test
    public void testGetProduct() {
        // In this test we get the first product twice to make sure it
        // is not affected by our call. Then we vend it and get the next 
        // one to ensure that the first one has now been released by
        // the vend call and the next one is first in line

        // Make sure that we get the next product in line only
        Product testP = dao.getProduct("Coke");
        assertEquals(testP.getProductName(), "Coke #1");

        testP = dao.getProduct("Coke");
        assertEquals(testP.getProductName(), "Coke #1");

        // Vend one and check to verify the next one in line is #2
        try {
            dao.vendItem("Coke");
            testP = dao.getProduct("Coke");
            assertEquals(testP.getProductName(), "Coke #2");
        } catch (NoItemInventoryException e) {
            fail("Exception should not have been thrown...");
        }

    }

    public Map<String, List<Product>> readDummyInventory() {

        Map<String, List<Product>> inventory = new HashMap<>();

        Product p1 = new Product();
        p1.setProductName("Coke #1");
        p1.setBestBy(LocalDate.parse("11/09/2019", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        p1.setInformation("Information");
        p1.setMessage("My Message");

        Product p2 = new Product();
        p2.setProductName("Coke #2");
        p2.setBestBy(LocalDate.parse("11/09/2019", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        p2.setInformation("Information");
        p2.setMessage("My Message");

        Product p3 = new Product();
        p3.setProductName("Coke #3");
        p3.setBestBy(LocalDate.parse("11/09/2019", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        p3.setInformation("Information");
        p3.setMessage("My Message");

        List<Product> cokelist = new ArrayList<>();

        cokelist.add(p1);
        cokelist.add(p2);
        cokelist.add(p3);
        
        inventory.put("Coke", cokelist);

        return inventory;
    }

}
