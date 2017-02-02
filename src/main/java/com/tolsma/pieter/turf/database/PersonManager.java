package com.tolsma.pieter.turf.database;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
		persons = readFromFile();
	}

	
	public static PersonManager getInstance() {
		return instance;
	}

	public ArrayList<Person> getPersons() {
		return persons;
	}
	
	public void save(ArrayList<Person> newList) {
		JSONObject main = new JSONObject();
		JSONArray personsArray = new JSONArray();
		
		for (Person p : newList) {
			JSONObject pObject = new JSONObject();
			pObject.put("name", p.getName());
			pObject.put("id", p.getId().toString());
			pObject.put("balance", String.valueOf(p.getBalance()));
			personsArray.add(pObject);
		}
		main.put("persons", personsArray);
		
		try (FileWriter writer = new FileWriter(new File(FILE_PATH))) {
			writer.write(main.toJSONString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.persons = readFromFile();
	}
	
	public void save() {
		save(persons);
	}
	
	public SortedSet<Map.Entry<Person, Integer>> getOrderedConsumption(Date startDate, Date endDate, Category itemCategory) {
		Map<Person, Integer> tree = new HashMap<Person, Integer>();
		
		for (Person p : this.getPersons()) {
			int count = p.getConsumptionAmount(startDate, endDate, itemCategory);
			tree.put(p, count);
		}
		
		return (SortedSet<Map.Entry<Person, Integer>>) DateHelper.entriesSortedByValues(tree);
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
		for (Person p : getSelectedPersons()) {
			p.setSelected(false);
		}
	}
	
	private ArrayList<Person> readFromFile() {
		ArrayList<Person> results = new ArrayList<>();
		JSONParser parser = new JSONParser();

		File file = new File(FILE_PATH);
		if (!file.exists()) {
			try{
				JSONObject obj = new JSONObject();
				obj.put("persons", new JSONArray());
				FileWriter writer = new FileWriter(file);
				writer.write(obj.toJSONString());
				writer.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		try (FileReader reader = new FileReader(file)) {
			Object obj = parser.parse(reader);
			JSONObject jsonObject = (JSONObject) obj;

			JSONArray names = (JSONArray) jsonObject.get("persons");
			if (names.isEmpty()) return results;
			Iterator<JSONObject> it = names.iterator();
			while (it.hasNext()) {
				JSONObject obj1 = it.next();
				Person p = new Person((String) obj1.get("name"), UUID.fromString((String) obj1.get("id")), Float.parseFloat((String) obj1.get("balance")));
				results.add(p);
			}

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return results;
	}
}
