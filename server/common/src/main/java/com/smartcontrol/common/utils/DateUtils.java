package com.smartcontrol.common.utils;

import java.util.Calendar;

import com.google.type.Date;

public class DateUtils {
    
    public static Date convertToGoogleDate(java.util.Date date) {
        Calendar calendar = convertToCalendar(date);
        if (calendar == null) {
            return null;
        }
        
        Date.Builder dateBuilder = Date.newBuilder();
        dateBuilder.setYear(calendar.get(Calendar.YEAR));
        dateBuilder.setMonth(calendar.get(Calendar.MONTH) + 1);
        dateBuilder.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        
        return dateBuilder.build();
    }

    public static Calendar convertToCalendar(java.util.Date date) {
        if (date == null) {
            return null;
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static java.util.Date convertToJavaDate(Date googleDate) {
        if (googleDate == null) {
            return null;
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, googleDate.getYear());
        calendar.set(Calendar.MONTH, googleDate.getMonth() - 1); // Month is 0-based in Calendar
        calendar.set(Calendar.DAY_OF_MONTH, googleDate.getDay());
        
        return calendar.getTime();
    }
}
