package com.tolsma.pieter.turf.database;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import com.tolsma.pieter.turf.util.DateHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.tolsma.pieter.turf.Application;
import com.tolsma.pieter.turf.items.Item;
import com.tolsma.pieter.turf.items.Item.Category;
import com.tolsma.pieter.turf.items.Person;
import com.tolsma.pieter.turf.items.Transaction;

public class TransactionManager {

	private static TransactionManager instance = new TransactionManager();

	private String file_dir = Application.DATA_DIR + "/transactions/";

	private ArrayList<Transaction> transactions;

	/**
	 * Private constructor of this class.
	 * Initiated once.
	 */
	private TransactionManager() {
		File dir = new File(file_dir);
		if (!dir.exists()) {
			dir.mkdir();
		}
	}

	/**
	 * Read the transactions of the current week
	 * and store them in the transactions variable.
	 */
	public void init() {
		transactions = readTransactions();
	}

	public static TransactionManager getInstance() {
		return instance;
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * Returns a list of transactions that occurred in the week of
	 * the given date.
	 * @param date the data in the week you want the transactions of.
	 * @return list of transactions in that week.
	 */
	public ArrayList<Transaction> getTransactionsAt(Date date) {
		ArrayList<Transaction> result = readTransactions();
		
		Iterator<Transaction> it = result.iterator();
		while(it.hasNext()) {
			Transaction trans = it.next();
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
			if (!fmt.format(trans.getDate()).equals(fmt.format(date))) {
				it.remove();
			}
		}
		
		return result;
	}

	public ArrayList<Transaction> getTransactionsFromProduct(Date date, UUID productId) {
        ArrayList<Transaction> res = getTransactionsAt(date);

        ArrayList<Transaction> realRes = new ArrayList<>();

        for (Transaction t: res ){
            if (t.getItem().getId().equals(productId)) realRes.add(t);
        }
        return realRes;
    }

    public int getCountFromProductPerson(Date date, UUID productId, Person p) {
        ArrayList<Transaction> res = getTransactionsFromProduct(date, productId);
        int count = 0;
        for (Transaction t: res) {
            if (t.getParticipants().contains(p)) {
                count += t.getCount() / t.getParticipants().size();
            }
        }
        return count;
    }

    public int getMonthCountFromProductPerson(Date startDate, Date endDate, UUID productId, Person p) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(startDate);
        cal2.setTime(endDate);

        int weekDiff = cal2.get(Calendar.WEEK_OF_YEAR) - cal1.get(Calendar.WEEK_OF_YEAR);
        int count = 0;
        for (int i = 0; i < weekDiff; i++) {
            count += getCountFromProductPerson(cal1.getTime(), productId, p);
            cal1.add(Calendar.WEEK_OF_YEAR, 1);
        }
        return count;
    }

	/**
	 * Removes a specific transaction from the memory.
	 * @param transactionId UUID of the transaction you want to remove.
	 */
	public void removeTransaction(UUID transactionId) {
	    transactions = readTransactions();
		Iterator<Transaction> it = transactions.iterator();
		while(it.hasNext()) {
			Transaction t = it.next();
			if (t.getId().equals(transactionId)) {
				ItemManager.getInstance().getItem(t.getItem().getId()).subtractStock(-t.getCount());
				it.remove();
				overwriteFile();
				return;
			}
		}
	}

	public void writeTransaction(Transaction trans) {
	    transactions = readTransactions();
	    transactions.add(trans);
	    overwriteFile();
    }

	/**
	 * Writes the current memory of transactions to the weekly file.
	 */
	public void overwriteFile() {
		JSONObject main = new JSONObject();
		JSONArray transactionsArray = new JSONArray();
		System.out.println("Saving file for " + transactions.size() + " transactions");
		for (Transaction t : transactions) {
			transactionsArray.add(t.getTransactionJSON());
		}

		main.put("transactions", transactionsArray);

		try (FileWriter writer = new FileWriter(new File(getFilePath(new Date())))) {
			writer.write(main.toJSONString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addTransaction(Transaction trans) {
		writeTransaction(trans);
	}

	/**
	 * Parses date to filepath
	 * @param date
	 * @return filepath in the format WEEK_YEAR.json
	 */
	public String getFilePath(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return file_dir + cal.get(Calendar.WEEK_OF_YEAR) + "_" + cal.get(Calendar.YEAR) + ".json";
	}

	private ArrayList<Transaction> readTransactions() {
		ArrayList<Transaction> res = new ArrayList<>();
		try {
			ResultSet set = DatabaseHelper.getDB().query("SELECT * FROM transactions");
			while (set.next()) {
				String[] personUuids = set.getString("participants").split(",");
				ArrayList<Person> persons = new ArrayList<>();
				for (int i = 0; i < personUuids.length; i++) {
					Person p = PersonManager.getInstance().getPerson(UUID.fromString(personUuids[i]));
					if (p == null) System.out.println("FUUUUUU");
					persons.add(p);
				}
				UUID itemId = UUID.fromString(set.getString("item_identifier"));
				Item item = ItemManager.getInstance().getItem(itemId);
				UUID transId = UUID.fromString(set.getString("identifier"));
				int quantity = set.getInt("total_amount");
				String dateString = set.getString("created_at");
				DateFormat smp = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
				Date date = null;
				try {
					date = smp.parse(dateString);
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}

				Transaction t = new Transaction(item, quantity, persons, transId, date);
				res.add(t);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}
}
