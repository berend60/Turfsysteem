package com.tolsma.pieter.turf.util;

import com.tolsma.pieter.turf.Application;
import com.tolsma.pieter.turf.database.ItemManager;
import com.tolsma.pieter.turf.database.PersonManager;
import com.tolsma.pieter.turf.database.TransactionManager;
import com.tolsma.pieter.turf.items.Item;
import com.tolsma.pieter.turf.items.Person;
import com.tolsma.pieter.turf.items.Transaction;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by pietertolsma on 6/4/17.
 */
public class MonthlyMailJob implements org.quartz.Job {

    public static final String smpt = "smtp.gmail.com";
    public static final String user = "virgielonline@gmail.com";
    public static final String password = "434D$3d!@";

    public String path = Application.DATA_DIR + "/tmp.xls";

    public MailService mailService;

    public MonthlyMailJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.err.println("Hello World!  MyJob is executing.");
        mailService = new MailService(user, smpt, user, password);
        ArrayList<File> files = new ArrayList<File>();

        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        cal2.add(Calendar.WEEK_OF_YEAR, -5);
        files.add(generateFile(cal2.getTime(), cal.getTime()));

        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        String timeString = "Week " + cal2.get(Calendar.WEEK_OF_YEAR) + " t/m " + cal.get(Calendar.WEEK_OF_YEAR);
        String message = "Beste Huisgenoot, <br/><br/> Bijgevoegd zit het BEER overzicht van " + timeString + "." +
                "<br/> Mochten er vragen of opmerkingen zijn, neem dan contact op met Janus. <br/> Lekker gezopen deze week! <br/><br/> Joe, <br/> Turfsysteem" +
                "<br/><hr/> <i>(Dit is een automatisch gegenereerd bericht. Reageren heeft geen zin.)</i>";
        mailService.send(new String[]{"epieter.tolsma@gmail.com", "frisovanwassenaer@gmail.com"}, "WATEX BEER " + timeString, message, files);
    }

    public File generateFile(Date beginDate, Date date) {
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
                    int count = TransactionManager.getInstance().getMonthCountFromProductPerson(beginDate, date, items.get(i).getId(), persons.get(k));
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
