package com.tolsma.pieter.turf.items;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by pietertolsma on 2/2/17.
 */
public class ItemTest {

    private Item item1;
    private Item item2;

    @Before
    public void startUp() {
        item1 = new Item("Test", 1.0f, true, Item.Category.ETEN, UUID.randomUUID(), 1);
        item2 = new Item("Test", 1.0f, true, Item.Category.ETEN, UUID.randomUUID(), 1);
    }

    @Test
    public void equalsSameObject() {
        assertTrue("Equals() should return true when same object is given", item1.equals(item1));
    }

    @Test
    public void equalsDifferentObject() {
        assertFalse("Equals() should return false when different object type is given", item1.equals("Test"));
    }

    @Test
    public void equals_other_item() {
        assertFalse("Equals() should return false when other item is given", item1.equals(item2));
    }

    @Test
    public void getStock() {
        assertEquals("getStock() should return correct stock", item1.getStock(), 1);
    }

    @Test
    public void testAvailability() {
        assertEquals("getAvailability() should return correct value", item1.getAvailability(), true);
    }

    @Test
    public void setCategory() {
        item1.setCategory(Item.Category.PILS);
        assertEquals("Changed category should be changed", item1.getCategory(), Item.Category.PILS);
    }

    @Test
    public void getCategory() {
        assertEquals("getCategory() should return the correct category", item1.getCategory(), Item.Category.ETEN);
    }

    @Test
    public void setPrice() {
        item1.setPrice(2.0f);
        assertTrue("setPrice() should return new value", item1.getPrice() == 2.0f);
    }

    @Test
    public void setName() {
        item1.setName("Beer");
        assertEquals("setName() should reflect the name-change", item1.getName(), "Beer");
    }

    @Test
    public void setStock() {
        item1.setStock(2);
        assertEquals("setStock() should reflect the stock-change", item1.getStock(), 2);
    }

    @Test
    public void subtractStockPositive() {
        item1.subtractStock(1);
        assertEquals("subtractStock() should reflect the stock change", item1.getStock(), 0);
    }

    @Test
    public void subtractStockNegative() {
        item1.subtractStock(-1);
        assertEquals("subtractStock() should reflect negative stock change", item1.getStock(), 2);
    }
}
