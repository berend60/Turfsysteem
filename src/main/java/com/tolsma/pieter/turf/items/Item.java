package com.tolsma.pieter.turf.items;

import java.util.UUID;

public class Item {

	public static enum Category {
		SPECIAALBIER, FRIS, ETEN, PILS
	}

	private String name;
	private float price;
	private boolean available;
	private Category category;
	private UUID id;
	private int inStock;

	public Item(String name, float price, boolean available, Category category, UUID id, int inStock) {
		this.name = name;
		this.price = price;
		this.available = available;
		this.category = category;
		this.id = id;
		this.inStock = inStock;
	}

	public Category getCategory() {
		return category;
	}
	
	public int getStock() {
		return inStock;
	}
	
	public void subtractStock(int amount) {
		inStock -= amount;
	}
	
	public void setStock(int amount) {
		this.inStock = amount;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setCategory(Category cat) {
		this.category = cat;
	}

	public boolean getAvailability() {
		return available;
	}

}
