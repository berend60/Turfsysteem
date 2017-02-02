package com.tolsma.pieter.turf.util;

import org.junit.Test;

import java.util.Calendar;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by pietertolsma on 2/2/17.
 */
public class DateHelperTest {

    @Test
    public void isInTimeslotTest() {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.set(Calendar.DAY_OF_YEAR, 1);
        cal2.set(Calendar.DAY_OF_YEAR, 2);

        cal1.set(Calendar.HOUR_OF_DAY, 9);
        cal2.set(Calendar.HOUR_OF_DAY, 1);

        assertTrue("isInTimeSlot() should return true when two dates differ less than 24 hours from 8am to 8am",
                DateHelper.isInTimeslot(cal1.getTime(), cal2.getTime()));

        cal2.set(Calendar.HOUR_OF_DAY, 8);

        assertFalse("isInTimeSlot() should return false when two dates are not in same timeslot",
                DateHelper.isInTimeslot(cal1.getTime(), cal2.getTime()));

        cal2.set(Calendar.DAY_OF_YEAR, 1);
        assertTrue("isInTimeSlot() should return true when dates are on same day and later than 8am",
                DateHelper.isInTimeslot(cal1.getTime(), cal2.getTime()));

        cal2.set(Calendar.HOUR_OF_DAY, 1);
        assertFalse("isInTimeSlot() should return false when dates are on same day but one is before 8am",
                DateHelper.isInTimeslot(cal1.getTime(), cal2.getTime()));

        cal1.set(Calendar.HOUR_OF_DAY, 1);
        assertTrue("isInTimeSlot() should return true when dates are on same day and before 8am",
                DateHelper.isInTimeslot(cal1.getTime(), cal2.getTime()));

    }
}
