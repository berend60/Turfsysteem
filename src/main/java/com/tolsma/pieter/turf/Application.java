package com.tolsma.pieter.turf;

import java.io.File;

import com.tolsma.pieter.turf.database.ItemManager;
import com.tolsma.pieter.turf.database.PersonManager;
import com.tolsma.pieter.turf.database.TransactionManager;
import com.tolsma.pieter.turf.gui.MainFrame;

public class Application {
	
	private static MainFrame mainFrame;
	
	public static final String DATA_DIR = "./turfdata";
	
	public static final String PASSWORD_LEVEL_1 = "watexSS9";
	public static final String PASSWORD_LEVEL_2 = "fuhrerdolf";

	public static void main(String[] args) {
		checkDataFolder();
		mainFrame = new MainFrame();
	}
	
	public static void checkDataFolder() {
		File dir = new File(DATA_DIR);
		if (!dir.exists()) {
			dir.mkdir();
		}
		PersonManager.getInstance().init();
		ItemManager.getInstance().init();
		TransactionManager.getInstance().init();
	}
	
}
