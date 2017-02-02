package com.tolsma.pieter.turf.util;

import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

public class DateHelper {
	
	public static int daysBetween(Date d1, Date d2){
        int result = (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
        
        if (result < 1) return 1;
        return result;
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
