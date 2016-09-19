package com.example.testffmpeg;

/**
 * Created by android on 2016/9/12.
 */

public class MyTimeZone {
    //获取当前时区代号
    public static int getCurTimeZoneNumber(){
        int ss=0;
        switch (getCurrentTimeZone()) {
            case "GMT-12:00":
                ss = 0;
                break;
            case "GMT-11:00":
                ss = 1;
                break;
            case "GMT-10:00":
                ss = 2;
                break;
            case "GMT-09:00":
                ss = 3;
                break;
            case "GMT-08:00":
                ss = 4;
                break;
            case "GMT-07:00":
                ss = 5;
                break;
            case "GMT-06:00":
                ss = 6;
                break;
            case "GMT-05:00":
                ss = 7;
                break;
            case "GMT-04:00":
                ss = 8;
                break;
            case "GMT-03:00":
                ss = 9;
                break;
            case "GMT-02:00":
                ss = 10;
                break;
            case "GMT-01:00":
                ss = 11;
                break;
            case "GMT+00:00":
                ss = 12;
                break;
            case "GMT+01:00":
                ss = 13;
                break;
            case "GMT+02:00":
                ss = 14;
                break;
            case "GMT+03:00":
                ss = 15;
                break;
            case "GMT+04:00":
                ss = 16;
                break;
            case "GMT+05:00":
                ss = 17;
                break;
            case "GMT+06:00":
                ss = 18;
                break;
            case "GMT+07:00":
                ss = 19;
                break;
            case "GMT+08:00":
                ss = 20;
                break;
            case "GMT+09:00":
                ss = 21;
                break;
            case "GMT+10:00":
                ss = 22;
                break;
            case "GMT+11:00":
                ss = 23;
                break;
            case "GMT+12:00":
                ss = 24;
                break;
        }
        return ss;
    }
    public static String getCurrentTimeZone()
    {
        java.util.TimeZone tz = java.util.TimeZone.getDefault();
        return createGmtOffsetString(true,true,tz.getRawOffset());
    }
    public static String createGmtOffsetString(boolean includeGmt,
                                               boolean includeMinuteSeparator, int offsetMillis) {
        int offsetMinutes = offsetMillis / 60000;
        char sign = '+';
        if (offsetMinutes < 0) {
            sign = '-';
            offsetMinutes = -offsetMinutes;
        }
        StringBuilder builder = new StringBuilder(9);
        if (includeGmt) {
            builder.append("GMT");
        }
        builder.append(sign);
        appendNumber(builder, 2, offsetMinutes / 60);
        if (includeMinuteSeparator) {
            builder.append(':');
        }
        appendNumber(builder, 2, offsetMinutes % 60);
        return builder.toString();
    }

    private static void appendNumber(StringBuilder builder, int count, int value) {
        String string = Integer.toString(value);
        for (int i = 0; i < count - string.length(); i++) {
            builder.append('0');
        }
        builder.append(string);
    }
}
