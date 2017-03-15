package com.tolsma.pieter.turf.util;

import com.tolsma.pieter.turf.items.Person;

import java.util.*;

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
}
