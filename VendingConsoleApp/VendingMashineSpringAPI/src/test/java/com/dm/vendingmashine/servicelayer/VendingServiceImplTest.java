/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.servicelayer;

import com.dm.vendingmashine.dao.FileIOException;
import com.dm.vendingmashine.dao.InventoryDao;
import com.dm.vendingmashine.dao.InventoryDaoImpl;
import com.dm.vendingmashine.dao.NoItemInventoryException;
import com.dm.vendingmashine.dto.Money;
import com.dm.vendingmashine.dto.Product;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Danimaetrix
 */
public class VendingServiceImplTest {

    VendingService service;
    InventoryDao daoInv;

    public VendingServiceImplTest() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        this.service = ctx.getBean("vendingService", VendingServiceImpl.class);
        this.daoInv = ctx.getBean("inventoryDao", InventoryDaoImpl.class);

    }

    @Before
    public void setUp() throws FileIOException {
        daoInv.setInventory(readDummyInventory());
        service.setPricing(loadFakePricingFromFile());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testReturnPriceArrayWithStatus() {
        // The list contents themselves are a pass-through into the array,
        // so they do not need to be checked.  Here, we are only interested
        // in making sure that the array is appended with the correct
        // modifier to indicate its status

        List<String[]> testList = service.returnPriceArrayWithStatus();

        // Make sure our Coke inventory contains no sold out status
        assertEquals(testList.get(0)[2], "");

        // The next item should be sold out, since we only added Coke. 
        // It's status should NOT be blank
        assertNotEquals(testList.get(1)[2], "");

    }

    @Test
    public void testVendProduct() throws Exception {
        BigDecimal cash = new BigDecimal("10.00");
        Money m = new Money(cash);
        // This is a redundant test since the method is pass-through
        // It is implemented as a final check on a core function

        // Make sure vend order decreases quantity by 1
        int before = daoInv.getProductQuantity("Coke");
        Product p = service.vendProduct(m, "Coke");
        int after = daoInv.getProductQuantity("Coke");
        assertEquals(1, before - after);

        // Verify that the first item vended is Coke #1
        assertEquals(p.getProductName(), "Coke #1");

        // Verify that the second item vended is Coke #2
        p = service.vendProduct(m, "Coke");
        assertEquals(p.getProductName(), "Coke #2");
    }

    @Test
    public void testIsSoldOut() {
        // We empty the inventory of coke in this test to 
        // make sure the sold out status updates
        BigDecimal cash = new BigDecimal("10.00");
        Money m = new Money(cash);

        // Expect false -- we initialized with 3 available
        assertEquals(service.isSoldOut("Coke"), false);

        try {
            service.vendProduct(m, "Coke");
            service.vendProduct(m, "Coke");
            service.vendProduct(m, "Coke");

        } catch (NoItemInventoryException | InsufficientFundsException e) {
            fail("We should not reach this");
        }

        // Expect true -- we vended all 3 coke items
        assertEquals(service.isSoldOut("Coke"), true);
    }

    @Test
    public void testValidateMoney() throws InsufficientFundsException {
        // Simple comparison of user's money to cost of object

        BigDecimal cash = new BigDecimal("2.50");
        Money m = new Money(cash);

        // Overpayment -- expect valid
        assertTrue(service.validateMoney(m, "Coke"));

        // Underpayment -- expect invalid
        try {
            m.setTotalmoney(BigDecimal.valueOf(1.49));
            assertFalse(service.validateMoney(m, "Coke"));
            fail("Exception was not thrown");
        } catch (InsufficientFundsException e) {
            // Sucesss!
        }
        // Exact payment -- expect valid
        m.setTotalmoney(BigDecimal.valueOf(1.50));
        assertTrue(service.validateMoney(m, "Coke"));

    }

    @Test
    public void testCalculateChange() {
        // Change breakdown is tested in the money test.  
        // Here, we just verify that correct change is being
        // given.

        BigDecimal cash = new BigDecimal("2.50");
        Money m = new Money(cash);

        // Try to overpay - expect 1.00 back
        m = service.calculateChange(m, "Coke");

        assertEquals(m.getTotalmoney(), BigDecimal.valueOf(1).setScale(2));

        m.setTotalmoney(BigDecimal.valueOf(1.50));

        // Try to use exact change - expect 0.00 back
        m = service.calculateChange(m, "Coke");

        assertEquals(m.getTotalmoney(), BigDecimal.valueOf(0).setScale(2));

        // Try to underpay -- expect exception
        service.calculateChange(m, "Coke");

    }

    @Test
    public void testUpdateInventory() throws Exception {

        // trivial pass through - no testing required
    }

    @Test
    public void testCheckForFileIOErrors() {

        // trivial pass through - no testing required
    }

    @Test
    public void testReturnPriceMap() {

        // trivial pass through - no testing required
    }

    @Test
    public void testGetProduct() {

        // trivial pass through - no testing required
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

    public Map<String, String> loadFakePricingFromFile() {

        Map<String, String> prices = new LinkedHashMap<>();

        prices.put("Coke", "1.50");
        prices.put("Diet Coke", "1.50");
        prices.put("Sprite", "1.50");
        prices.put("Mello Yellow", "1.50");
        prices.put("Dasani", "1.75");

        return prices;
    }

}
