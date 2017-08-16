package com.bowool.gymnote;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by bowoo on 2017/8/16.
 */

public class DateManager {
    public long daysBetweenTwoDates(Date earlyDay, Date lateDay) {


        Calendar startCalendar = Calendar.getInstance();
        Calendar lateCalendar = Calendar.getInstance();
        startCalendar.setTime(earlyDay);
        lateCalendar.setTime(lateDay);

        long diff = lateCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();

        return diff / (24 * 60 * 60 * 1000);
    }
}
