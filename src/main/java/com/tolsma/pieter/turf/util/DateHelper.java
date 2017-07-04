package com.tolsma.pieter.turf.util;

import com.tolsma.pieter.turf.Application;
import com.tolsma.pieter.turf.database.ItemManager;
import com.tolsma.pieter.turf.database.PersonManager;
import com.tolsma.pieter.turf.database.TransactionManager;
import com.tolsma.pieter.turf.items.Item;
import com.tolsma.pieter.turf.items.Person;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

public class DateHelper {

    public static final String smpt = "smtp.gmail.com";
    public static final String user = "virgielonline@gmail.com";
    public static final String password = "434D$3d!@";

    public static final String path = Application.DATA_DIR + "/tmp.xls";
	
	public static int daysBetween(Date d1, Date d2){
        int result = (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
        
        if (result < 1) return 1;
        return result;
	}

	public static boolean isInTimeslot(Date targetDate, Date compareDate) {
	    Calendar cal1 = Calendar.getInstance();
	    Calendar cal2 = Calendar.getInstance();
	    cal1.setTime(targetDate);
	    cal2.setTime(compareDate);

	    if (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
	        if (cal2.get(Calendar.HOUR_OF_DAY) > 7) {
	            return true;
            } else if (cal1.get(Calendar.HOUR_OF_DAY) < 8 && cal2.get(Calendar.HOUR_OF_DAY) < 8) {
	            return true;
            }
        } else if (cal1.get(Calendar.DAY_OF_YEAR) + 1 == cal2.get(Calendar.DAY_OF_YEAR)) {
	        if (cal2.get(Calendar.HOUR_OF_DAY) < 8) {
	            return true;
            }
        }
        return false;
    }
	
	public static HashMap<Person, Integer> entriesSortedByValues(Map<Person, Integer> map) {
		List<Map.Entry<Person, Integer>> list = new LinkedList<>(map.entrySet());
        Collections.sort( list, new Comparator<Map.Entry<Person, Integer>>()
        {
            @Override
            public int compare( Map.Entry<Person, Integer> o1, Map.Entry<Person, Integer> o2 )
            {
                return ( o2.getValue() ).compareTo( o1.getValue() );
            }
        } );
        HashMap<Person, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<Person, Integer> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
	}

	public static void sendBEER(Date date, String[] to) {
        MailService mailService = new MailService(user, smpt, user, password);
        ArrayList<File> files = new ArrayList<File>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        files.add(generateFile(cal.getTime()));

        int week = cal.get(Calendar.WEEK_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        String message = "Beste Huisgenoot, <br/><br/> Bijgevoegd zit het BEER overzicht van week " + week + " " + year + "." +
                "<br/> Mochten er vragen of opmerkingen zijn, neem dan contact op met Janus. <br/> Lekker gezopen deze week! <br/><br/> Joe, <br/> Turfsysteem" +
                "<br/><hr/> <i>(Dit is een automatisch gegenereerd bericht. Reageren heeft geen zin.)</i>";
        mailService.send(to, "WATEX BEER Week " + week + " " + year, message, files);
    }

    private static File generateFile(Date date) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("FirstSheet");

            // USER ROW
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell(0).setCellValue("Product");
            ArrayList<Person> persons = PersonManager.getInstance().getPersons();
            for (int i = 0; i < persons.size(); i++) {
                rowhead.createCell(i + 1).setCellValue(persons.get(i).getName());
            }

            // CREATE ROW FOR EVERY ITEM
            ArrayList<Item> items = ItemManager.getInstance().getAllItems();
            for (int i = 0; i < items.size(); i++) {
                HSSFRow aRowHead = sheet.createRow((short) i + 1);
                aRowHead.createCell(0).setCellValue(items.get(i).getName());

                for (int k = 0; k < persons.size(); k++) {
                    int count = TransactionManager.getInstance().getCountFromProductPerson(date, items.get(i).getId(), persons.get(k));
                    aRowHead.createCell(k + 1).setCellValue(count);
                }
            }

            FileOutputStream fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("Your excel file has been generated!");

            return new File(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
