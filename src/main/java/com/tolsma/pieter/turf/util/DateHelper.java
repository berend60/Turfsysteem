package com.tolsma.pieter.turf.util;

import java.util.*;
import java.util.Map.Entry;

public class DateHelper {
	
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
	
	public static <Person, Integer extends Comparable<? super Integer>> SortedSet<Map.Entry<Person,Integer>> entriesSortedByValues(Map<Person, Integer> map) {
		SortedSet<Map.Entry<Person, Integer>> sortedEntries = new TreeSet<Map.Entry<Person, Integer>>(
				new Comparator<Map.Entry<Person, Integer>>() {
					@Override
					public int compare(Entry<Person, Integer> o1, Entry<Person, Integer> o2) {
						int res =  o2.getValue().compareTo(o1.getValue());
						return res;
					}
				});
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}
}
