package com.tolsma.pieter.turf;

import java.io.File;
import java.util.Date;
import com.tolsma.pieter.turf.database;
import com.tolsma.pieter.turf.database.DatabaseHelper;
import com.tolsma.pieter.turf.database.ItemManager;
import com.tolsma.pieter.turf.database.PersonManager;
import com.tolsma.pieter.turf.database.TransactionManager;
import com.tolsma.pieter.turf.gui.MainFrame;
import com.tolsma.pieter.turf.util.DatabaseURLRetriever;
import com.tolsma.pieter.turf.util.MailJob;
import com.tolsma.pieter.turf.util.MonthlyMailJob;
import org.quartz.*;

import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;

public class Application {
	public static final String DATA_DIR = "./turfdata";
	
	public static final String PASSWORD_LEVEL_1 = "watexSS9";
	public static final String PASSWORD_LEVEL_2 = "fuhrerdolf";

	public static final String smpt = "smtp.gmail.com";
	public static final String user = "virgielonline@gmail.com";
	public static final String password = "434D$3d!@";

	public static String databaseURL = "";


	public static void main(String[] args) {
		checkDataFolder();
		new MainFrame();
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

	private static void setupScheduler() throws SchedulerException{
		JobDetail job = newJob(MailJob.class)
				.withIdentity("mailing", "beer")
				.build();

		JobDetail monthJob = newJob(MonthlyMailJob.class)
				.withIdentity("monthlymailing", "beer")
				.build();

		Date date = new Date();
		date.setMonth(6);
		date.setDate(5);

		String exp = "0 9 9 ? * MON *";
		String exp2 = "0 9 9 1/28 * ? *";
		Trigger trigger = TriggerBuilder.newTrigger()
				.startNow()
				.withSchedule(
						CronScheduleBuilder.cronSchedule(exp))
				.build();

		Trigger trigger2 = TriggerBuilder.newTrigger()
				.startAt(date)
				.withSchedule(
						CronScheduleBuilder.cronSchedule(exp2))
				.build();

		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
		scheduler.scheduleJob(monthJob, trigger2);

	}
}
