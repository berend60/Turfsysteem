package com.tolsma.pieter.turf.items;

import com.tolsma.pieter.turf.database.ItemManager;
import com.tolsma.pieter.turf.database.PersonManager;
import com.tolsma.pieter.turf.database.TransactionManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by pietertolsma on 2/2/17.
 */
public class TransactionTest {

    private Item item1, item2;
    private Person p1, p2;
    private ArrayList<Person> parts1, parts2;

    private UUID transId1;
    private Date date1;
    private Transaction trans1, trans2;
    @Before
    public void startUp() {
        PersonManager.getInstance().init();
        ItemManager.getInstance().init();
        TransactionManager.getInstance().init();
        item1 = new Item("Test", 1.0f, true, Item.Category.ETEN, UUID.randomUUID(), 1);
        item2 = new Item("Test", 1.0f, true, Item.Category.ETEN, UUID.randomUUID(), 1);
        p1 = new Person("Test", UUID.randomUUID(), 10f);
        p2 = new Person("Test", UUID.randomUUID(), 9f);
        parts1 = new ArrayList<>();
        parts2 = new ArrayList<>();

        parts2.add(p1);
        parts2.add(p2);

        transId1 = UUID.randomUUID();
        date1 = new Date();
        trans1 = new Transaction(item1, 5, parts1, transId1, date1);
        trans2 = new Transaction(item2, 4, parts2, UUID.randomUUID(), new Date());
    }

    @Test
    public void testConstructor1() {
        Transaction trans = new Transaction(item1);
        assertEquals("Transaction should be initialized with default values", trans.getCount(), 1);
    }

    @Test
    public void getId() {
        assertEquals("getId() should return correct value", trans1.getId(), transId1);
    }

    @Test
    public void getDate() {
        assertEquals("getDate() should return correct value", trans1.getDate(), date1);
    }

    @Test
    public void addCount() {
        trans1.addCount();
        assertEquals("getCount() should reflect the added count", trans1.getCount(), 6);
    }

    @Test
    public void sameParticipants() {
        assertTrue("sameParticipants() should compare the selected persons with this transactions persons", trans1.sameParticipants());
    }

    @Test
    public void getParticipantsString() {
        assertEquals("getParticipantsString() should return correct format", trans2.getParticipantsString(), "Test, Test, ");
    }

    @Test
    public void getItem() {
        assertEquals("getItem() should return the correct item", trans2.getItem(), item2);
    }

    @Test
    public void getTotalPrice() {
        assertTrue("getTotalPrice() should return correct value", trans1.getTotalPrice() == 5.00f);
    }

    @Test
    public void toStringTest() {
        assertEquals("toString() should return correct string", trans2.toString(), "<html>4x Test (a 1.0 p.s.) : Test, Test, </html>");
    }

    @Test
    public void getParticipants() {
        assertEquals("getParticipants() should return correct value", trans2.getParticipants(), parts2);
    }
}
