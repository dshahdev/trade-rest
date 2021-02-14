package com.shahs.transactions.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Date;

public class MiscUtils {

    public static int getIterableSize(Iterable iterableData ) {
        int counter = 0;
        for (Object i: iterableData) {
            counter++;
        }
        return counter;
    }

    public static java.sql.Date stringToSqlDate(String date, String format) {    // format example "MM/dd/yyyy"
        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        DateTime dt = formatter.parseDateTime(date);
        return new java.sql.Date(dt.getMillis());
    }
    public static java.sql.Date stringToDate(String date, String format) {    // format example "MM/dd/yyyy"
        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        DateTime dt = formatter.parseDateTime(date);
        return new java.sql.Date(dt.getMillis());


    }
    public static String dateToString(Date date, String format) {
        DateTime dt = new DateTime(date);
        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        return fmt.print(dt);
    }
//    public static Date stringToDate(String date, String format) {    // format example "MM/dd/yyyy"
//
//        SimpleDateFormat sdf = new SimpleDateFormat(format);
//        sdf.setTimeZone(TimeZone.getTimeZone("EDT"));
//        java.sql.Date sqlDate = null;
//        try {
//            Date utilDate = sdf.parse(date);
//            sqlDate = new java.sql.Date(utilDate.getTime());
//
//        } catch(ParseException e) {
//            e.printStackTrace();
//        }
//        return sqlDate;
//    }
//    public static String dateToString(Date date, String format) {
//
//        SimpleDateFormat sdf = new SimpleDateFormat(format);
//        String stringDate = "";
//        try {
//            stringDate = sdf.format(date);
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
//        return stringDate;
//    }
}
