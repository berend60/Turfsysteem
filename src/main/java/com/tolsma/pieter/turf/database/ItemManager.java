package com.tolsma.pieter.turf.database;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.tolsma.pieter.turf.Application;
import com.tolsma.pieter.turf.items.Item;
import com.tolsma.pieter.turf.items.Item.Category;

public class ItemManager {

	private static ItemManager instance = new ItemManager();

	public static final String FILE_PATH = Application.DATA_DIR + "/items.json";

	public final static UUID BEER_ID = UUID.fromString("1aee976f-ee69-4b98-b052-efdfb7f0dd4f");

	private Item bierItem;
	private ArrayList<Item> speciaalBierList;
	private ArrayList<Item> etenList;
	private ArrayList<Item> frisList;

	public ItemManager() {
	}

	public void init() {
		readItems();
	}

	public static ItemManager getInstance() {
		return instance;
	}

	public Item getBiertje() {
		return bierItem;
	}

	public ArrayList<Item> getSpeciaalBier() {
		return speciaalBierList;
	}

	public ArrayList<Item> getEten() {
		return etenList;
	}

	public ArrayList<Item> getFris() {
		return frisList;
	}

	public Item getItem(UUID id) {
		for (Item item : getAllItems()) {
			if (item.getId().equals(id)) {
				return item;
			}
		}
		return null;
	}

	public void readItems() {
		speciaalBierList = new ArrayList<>();
		etenList = new ArrayList<>();
		frisList = new ArrayList<>();
		bierItem = new Item("Bier", 0f, false, Category.PILS, BEER_ID, 1);
		ResultSet set = DatabaseHelper.getDB().query("SELECT * FROM items");
		try {
			while (set.next()) {
				System.out.println("READING ITEMS");
				Category cat = Category.PILS;
				switch (set.getString("category")) {
					case "speciaalbier":
						cat = Category.SPECIAALBIER;
						break;
					case "eten":
						cat = Category.ETEN;
						break;
					case "fris" :
						cat = Category.FRIS;
						break;
				}
				Item item = new Item(set.getString("name"), set.getFloat("price"), true, cat, UUID.fromString(set.getString("identifier")), set.getInt("quantity"));
				switch (item.getCategory()) {
					case SPECIAALBIER:
						speciaalBierList.add(item);
						break;
					case ETEN:
						etenList.add(item);
						break;
					case FRIS:
						frisList.add(item);
						break;
					case PILS:
						bierItem = item;
						break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Item> getAllItems() {
		ArrayList<Item> allItems = new ArrayList<>();
		allItems.addAll(speciaalBierList);
		allItems.addAll(etenList);
		allItems.addAll(frisList);
		allItems.add(bierItem);

		return allItems;
	}
}
