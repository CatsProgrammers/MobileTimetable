package com.cats.mobiletimetable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String unixTimeToString(Long unixTime){
        Date date = new Date();
        date.setTime(unixTime * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        return formatter.format(date);
    }
}
