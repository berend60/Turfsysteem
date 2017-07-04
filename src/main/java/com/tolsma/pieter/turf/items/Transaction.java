package com.tolsma.pieter.turf.items;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.tolsma.pieter.turf.database.PersonManager;

public class Transaction {

	private Item item;
	private int quantity;
	private ArrayList<Person> participants;
	private UUID id;
	private Date date;
	
	public Transaction(Item item) {
		this.participants = PersonManager.getInstance().getSelectedPersons();
		this.item = item;
		this.quantity = 1;
		this.id = UUID.randomUUID();
		this.date = new Date();
	}

	public Transaction(Item item, int quantity, ArrayList<Person> participants, UUID id, Date date) {
		this.item = item;
		this.quantity = quantity;
		this.participants = participants;
		this.id = id;
		this.date = (Date) date.clone();
	}
	
	public UUID getId() {
		return id;
	}
	
	public Date getDate() {
		return (Date) date.clone();
	}
	
	public void addCount() {
		quantity++;
	} 
	
	public int getCount() {
		return quantity;
	}
	
	public boolean sameParticipants() {
		return participants.equals(PersonManager.getInstance().getSelectedPersons());
	}
	
	public String getParticipantsString() {
		String res = "";
		for (Person p : participants) {
			res += p.getName() + ", ";
		}
		return res;
	}
	
	public Item getItem() {
		return item;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getTransactionJSON() {
		JSONObject obj = new JSONObject();
		
		JSONArray personIDArray = new JSONArray();
		for (Person p : participants) {
			personIDArray.add(p.getId().toString());
		}
		obj.put("participants", personIDArray);
		obj.put("item_id", item.getId().toString());
		obj.put("total_amount", String.valueOf(quantity));
		obj.put("transaction_id", id.toString());
		obj.put("created_at", String.valueOf(date.getTime()));
		
		return obj;
	}
	
	public float getTotalPrice() {
		float totalPrice = item.getPrice() * quantity;
		BigDecimal bd = new BigDecimal(Float.toString(totalPrice));
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}
	
	public ArrayList<Person> getParticipants() {
		return participants;
	}
	
	@Override
	public String toString() {
		String people = "";
		for (Person p : participants) {
			people += p.getName() + ", ";
		}
		return "<html>" + quantity + "x " + item.getName() + " (a " + item.getPrice() + " p.s.) : " + people + "</html>";
	}
	
	
	
}
