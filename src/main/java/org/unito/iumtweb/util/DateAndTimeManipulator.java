package org.unito.iumtweb.util;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateAndTimeManipulator {
    /**
     * Return LocalDate object from String date
     *
     * @param date String in format yyyy-MM-dd
     * @return LocalDate
     */
    public static LocalDate fromStringToLocalDate(String date) {
        return LocalDate.of(Integer.valueOf(date.split("-")[0]), Integer.valueOf(date.split("-")[1]), Integer.valueOf(date.split("-")[2]));
    }

    public static java.sql.Date fromStringToSqlDate(String date) {
        return java.sql.Date.valueOf(date);
    }
    
    public static java.sql.Time fromIntToSqlTime(int time) {
        return new java.sql.Time(time, 00, 00);
    }

    /**
     * Return LocalDate object from String date
     *
     * @param time String in format hh:mm:ss
     * @return LocalDate
     */
    public static LocalTime fromStringToLocalTime(String time) {
        return LocalTime.of(Integer.valueOf(time.split(":")[0]), Integer.valueOf(time.split(":")[1]), Integer.valueOf(time.split(":")[2]));
    }


    public static String fromLocalDateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
    
    public static String formatLocalDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    public static String formatTime(String time) {
        return time.substring(0, time.lastIndexOf(":"));
    }
}
