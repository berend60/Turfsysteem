package com.tolsma.pieter.turf.database;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
		readFromFile();
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

	public ArrayList<Item> getAllItems() {
		ArrayList<Item> allItems = new ArrayList<>();
		allItems.addAll(speciaalBierList);
		allItems.addAll(etenList);
		allItems.addAll(frisList);
		allItems.add(bierItem);

		return allItems;
	}

	@SuppressWarnings("unchecked")
	public void writeItems(ArrayList<Item> items) {
		JSONObject mainObject = getMainObject(items);

		try (FileWriter writer = new FileWriter(new File(FILE_PATH))) {
			writer.write(mainObject.toJSONString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		readFromFile();
	}
	
	public void save() {
		writeItems(getAllItems());
	}

	private JSONObject getMainObject(ArrayList<Item> items) {
		JSONObject mainObject = new JSONObject();
		JSONArray speciaalBier = new JSONArray();
		JSONArray eten = new JSONArray();
		JSONArray fris = new JSONArray();

		for (Item item : items) {
			JSONObject itemObject = new JSONObject();
			itemObject.put("name", item.getName());
			itemObject.put("price", String.valueOf(item.getPrice()));
			itemObject.put("available", String.valueOf(item.getAvailability()));
			itemObject.put("id", item.getId().toString());
			itemObject.put("in_stock", String.valueOf(item.getStock()));
			switch (item.getCategory()) {
			case SPECIAALBIER:
				speciaalBier.add(itemObject);
				break;
			case ETEN:
				eten.add(itemObject);
				break;
			case FRIS:
				fris.add(itemObject);
				break;
			case PILS:
				mainObject.put("bier", itemObject);
				break;
			}
		}
		mainObject.put("speciaalbier", speciaalBier);
		mainObject.put("eten", eten);
		mainObject.put("fris", fris);
		return mainObject;
	}

	private ArrayList<Item> parseJSON(JSONArray object, Category type) {
		ArrayList<Item> items = new ArrayList<>();

		@SuppressWarnings("unchecked")
		Iterator<JSONObject> it = object.iterator();
		while (it.hasNext()) {
			JSONObject item = it.next();
			items.add(parseItemJSON(item, type));
		}

		return items;
	}

	private Item parseItemJSON(JSONObject item, Category type) {
		String itemName = (String) item.get("name");
		float price = Float.valueOf((String) item.get("price"));
		boolean available = Boolean.valueOf((String) item.get("available"));
		UUID id = UUID.fromString((String) item.get("id"));
		String inStockString = (String) item.get("in_stock");
		int inStock = 0;
		if (inStockString != null)
			inStock = Integer.valueOf(inStockString);
		return new Item(itemName, price, available, type, id, inStock);
	}

	private void readFromFile() {
		JSONParser parser = new JSONParser();

		JSONObject bier = null;
		JSONArray speciaalBier = null;
		JSONArray fris = null;
		JSONArray eten = null;

		File file = new File(FILE_PATH);
		if (!file.exists()) {
			try {
				file.createNewFile();
				ArrayList<Item> dummyItems = new ArrayList<>();
				dummyItems.add(new Item("Default", 0f, false, Category.SPECIAALBIER, UUID.randomUUID(), 1));
				dummyItems.add(new Item("Default", 0f, false, Category.ETEN, UUID.randomUUID(), 1));
				dummyItems.add(new Item("Default", 0f, false, Category.FRIS, UUID.randomUUID(), 1));
				dummyItems.add(new Item("Bier", 0f, false, Category.PILS, BEER_ID, 1));
				FileWriter writer = new FileWriter(file);
				writer.write(getMainObject(dummyItems).toJSONString());
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		try (FileReader stream = new FileReader(new File(FILE_PATH))) {
			Object obj = parser.parse(stream);
			JSONObject jsonObject = (JSONObject) obj;

			bier = (JSONObject) jsonObject.get("bier");
			speciaalBier = (JSONArray) jsonObject.get("speciaalbier");
			fris = (JSONArray) jsonObject.get("fris");
			eten = (JSONArray) jsonObject.get("eten");
			stream.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		speciaalBierList = parseJSON(speciaalBier, Category.SPECIAALBIER);
		etenList = parseJSON(eten, Category.ETEN);
		frisList = parseJSON(fris, Category.FRIS);
		bierItem = parseItemJSON(bier, Category.PILS);
	}
}
