package com.tolsma.pieter.turf.database;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.tolsma.pieter.turf.Application;
import com.tolsma.pieter.turf.items.Item.Category;
import com.tolsma.pieter.turf.items.Person;
import com.tolsma.pieter.turf.util.DateHelper;

public class PersonManager {

	private static PersonManager instance = new PersonManager();

	public static final String FILE_PATH = Application.DATA_DIR + "/persons.json";

	private ArrayList<Person> persons;

	public PersonManager() {
		persons = null;
	}

	public void init() {
		persons = readPersons();
	}

	
	public static PersonManager getInstance() {
		return instance;
	}

	public ArrayList<Person> getPersons() {
		return readPersons();
	}
	
	public HashMap<Person, Integer> getOrderedConsumption(Date startDate, Date endDate, Category itemCategory) {
		Map<Person, Integer> tree = new HashMap<Person, Integer>();
		for (Person p : this.getPersons()) {
			int count = p.getConsumptionAmount(startDate, endDate, itemCategory);
			tree.put(p, count);
		}
		return (HashMap<Person, Integer>) DateHelper.entriesSortedByValues(tree);
	}

	public Person getPerson(UUID id) {
		for (Person p : persons) {
			if (p.getId().equals(id))
				return p;
		}
		return null;
	}

	public ArrayList<Person> getSelectedPersons() {
		ArrayList<Person> selected = new ArrayList<>();
		for (Person p : persons) {
			if (p.isSelected())
				selected.add(p);
		}
		return selected;
	}

	public void deselectAllPersons() {
		for (Person p : persons) {
			p.setSelected(false);
		}
	}

	private ArrayList<Person> readPersons() {
		ArrayList<Person> results = new ArrayList<>();
		try {
			ResultSet set = DatabaseHelper.getDB().query("SELECT * FROM persons WHERE active=TRUE ORDER BY anciennity ASC");
			while (set.next()) {
				Person p = new Person(set.getString("name"), UUID.fromString(set.getString("identifier")), set.getFloat("balance"));
				results.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
}
