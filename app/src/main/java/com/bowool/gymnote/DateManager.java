package com.bowool.gymnote;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by bowoo on 2017/8/16.
 */

public class DateManager {
    static public long daysBetweenTwoDates(Date earlyDay, Date lateDay) {


        Calendar startCalendar = Calendar.getInstance();
        Calendar lateCalendar = Calendar.getInstance();
        startCalendar.setTime(earlyDay);
        lateCalendar.setTime(lateDay);

        long diff = lateCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();

        return diff / (24 * 60 * 60 * 1000);
    }
    static public long dayToNow(Date earlyDay){
        if(earlyDay != null)
            return daysBetweenTwoDates(earlyDay,new Date());
        else
            return dayToNow(new Date());
    }
}
