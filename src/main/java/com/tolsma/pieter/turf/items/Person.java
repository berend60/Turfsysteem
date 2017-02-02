package com.tolsma.pieter.turf.items;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.tolsma.pieter.turf.database.TransactionManager;
import com.tolsma.pieter.turf.items.Item.Category;
import com.tolsma.pieter.turf.util.DateHelper;

public class Person {
	
	private String name;
	private UUID id;
	private float balance;
	private boolean isSelected = false;
	
	public Person(String name, UUID id, float balance) {
		this.name = name;
		this.id = id;
		this.balance = balance;
	}
	
	public void setSelected(boolean state) {
		isSelected = state;
	}
	
	public boolean isSelected() {
		return isSelected;
	}
	
	public int getConsumptionAmount(Date startDate, Date endDate, Category itemCategory) {
		int count = 0;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		
		for (int i = 0; i < DateHelper.daysBetween(startDate, endDate); i++) {
			cal.add(Calendar.DATE, +i);
			ArrayList<Transaction> res = TransactionManager.getInstance().getSpecificTransactions(cal.getTime(), this, itemCategory);
			for (Transaction t : res) {
				int amount = t.getCount() / t.getParticipants().size();
				count+= amount;
			}
		}
		
		return count;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addAmount(float amount) {
		this.balance += amount;
	}
	
	public void removeAmount(float amount) {
		this.balance -= amount;
	}
	
	public UUID getId() {
		return id;
	}
	
	public float getBalance() {
		BigDecimal bd = new BigDecimal(Float.toString(balance));
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}
	
	public void setBalance(float amount) {
		this.balance = amount;
	}

	public boolean equals(Object other) {
	    if (other instanceof Person) {
	        Person otherP = (Person) other;
	        if (otherP.getId().equals(this.id)) {
	            return true;
            }
            return false;
        }
	    return false;
    }
}
