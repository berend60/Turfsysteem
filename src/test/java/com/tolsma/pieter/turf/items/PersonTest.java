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
public class PersonTest {

    private UUID idP1;
    private Person person1, person2;

    @Before
    public void startUp() {
        idP1 = UUID.randomUUID();
        person1 = new Person("P1", idP1, 100);
        person2 = new Person("P2", UUID.randomUUID(), 1);
    }

    @Test
    public void setSelected() {
        person1.setSelected(true);
        assertTrue("getSelected() should reflect the state change", person1.isSelected());
    }

    @Test
    public void setName() {
        person1.setName("Willy");
        assertEquals("getName() should reflect the name change", person1.getName(), "Willy");
    }

    @Test
    public void addAmount() {
        person1.addAmount(1.0f);
        assertTrue("getBalance() should reflect balance change", person1.getBalance() == 101f);
    }

    @Test
    public void removeAmount() {
        person1.removeAmount(0.5f);
        assertTrue("getBalance() should reflect balance change", person1.getBalance() == 99.5f);
    }

    @Test
    public void setBalance() {
        person1.setBalance(1f);
        assertTrue("getBalance() should reflect balance change", person1.getBalance() == 1f);
    }

    @Test
    public void getId() {
        assertEquals("getId() should return the correct UUID", person1.getId(), idP1);
    }

    @Test
    public void equalsSameInstance() {
        assertEquals("Equals() should return true on same instance", person1, person1);
    }

    @Test
    public void equalsDifferentInstance() {
        assertFalse("Equals() should return false on different instance", person1.equals(person2));
    }

    @Test
    public void equalsDifferentObject() {
        assertFalse("Equals() should return false on different object argument,", person1.equals(1));
    }

}
