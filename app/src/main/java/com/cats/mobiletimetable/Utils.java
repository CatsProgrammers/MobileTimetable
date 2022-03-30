package com.cats.mobiletimetable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {


    public static String getCurrentDateString() {

        LocalDate localDate = LocalDate.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return localDate.format(formatter);
    }

    public static String getNextWeekDateString() {
        LocalDate localDate = LocalDate.now().plusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return localDate.format(formatter);
    }

}
