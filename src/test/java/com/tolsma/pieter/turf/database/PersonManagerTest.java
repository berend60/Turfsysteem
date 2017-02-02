package com.tolsma.pieter.turf.database;

import com.tolsma.pieter.turf.items.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Created by pietertolsma on 2/2/17.
 */
public class PersonManagerTest {

    private UUID personId;
    private Person p1;

    @Before
    public void startUp() {
        PersonManager.getInstance().init();
        personId = UUID.randomUUID();
        p1 = new Person("Test", personId, 10f);
    }

    @Test
    public void addPersonToDatabase() {
        ArrayList<Person> persons = PersonManager.getInstance().getPersons();
        persons.add(p1);

        PersonManager.getInstance().save(persons);
        assertEquals("Saved person should be returned as well", p1, PersonManager.getInstance().getPerson(personId));

        persons.remove(p1);
        PersonManager.getInstance().save(persons);
    }
}
