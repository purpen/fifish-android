package com.qiyuan.fifish.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lilin
 * created at 2016/4/26 19:55
 */
public class TimeUtil {
    public static int compareTo(String date1, String date2, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(dateFormat.parse(date1));
        calendar2.setTime(dateFormat.parse(date2));
        if (calendar1.getTimeInMillis()==calendar2.getTimeInMillis()) {
            return 0;
        } else if (calendar1.getTimeInMillis() > calendar2.getTimeInMillis()) {
            return 1;
        }else{
            return -1;
        }
    }

    public static long getMillonsecond(String dateStr, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        Date date = simpleDateFormat.parse(dateStr);
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    public static int getBetweenDays(long bingTime, long smallTime) {
        if (bingTime < smallTime) {
            throw new IllegalArgumentException("bingTime must not less than smallTime");
        }
        int days;
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTimeInMillis(bingTime);
        calendar2.setTimeInMillis(smallTime);
        days = calendar1.get(Calendar.DAY_OF_YEAR) - calendar2.get(Calendar.DAY_OF_YEAR);
        int year1 = calendar1.get(Calendar.YEAR);
        int year2 = calendar2.get(Calendar.YEAR);
        if (year1 == year2) {
            return days;
        } else {
            while (year1 != year2) {
                days += calendar2.getActualMaximum(Calendar.DAY_OF_YEAR);
                calendar2.add(Calendar.YEAR, 1);
                year2 = calendar2.get(Calendar.YEAR);
            }
            return days;
        }
    }
}
