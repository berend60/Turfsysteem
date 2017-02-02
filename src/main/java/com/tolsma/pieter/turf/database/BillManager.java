package com.tolsma.pieter.turf.database;

import java.util.ArrayList;

import com.tolsma.pieter.turf.items.Item;
import com.tolsma.pieter.turf.items.Transaction;
import com.tolsma.pieter.turf.items.Person;

public class BillManager {
	
	private static BillManager instance = new BillManager();

	private ArrayList<Transaction> elements;
	
	public BillManager() {
		elements = new ArrayList<>();
	}
	
	public static BillManager getInstance() {
		return instance;
	}
	
	public ArrayList<Transaction> getElements() {
		return elements;
	}
	
	public void turf() {
		for (Transaction item : elements) {
			ItemManager.getInstance().getItem(item.getItem().getId()).subtractStock(item.getCount());
			for (Person p : item.getParticipants()) {
				float amount = item.getItem().getPrice() * (item.getCount() / item.getParticipants().size());
				PersonManager.getInstance().getPerson(p.getId()).removeAmount(amount);
			}
			TransactionManager.getInstance().addTransaction(item);
		}
		ItemManager.getInstance().save();
		PersonManager.getInstance().save();
		elements.clear();
	}
	
}
