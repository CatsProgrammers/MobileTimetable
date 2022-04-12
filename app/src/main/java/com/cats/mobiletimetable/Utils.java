package com.cats.mobiletimetable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public final static String groupSettingsKey = "currentGroup";

    public final static String userTypeSettingsKey = "currentUserType";

    public final static String teacherSettingsKey = "currentTeacher";

    public static String stringFormatter(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        return formatter.format(date);
    }


    public static Date getNextWeekDate(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        return calendar.getTime();
    }

    public static String getNextWeekDateString(Date currentDate) {
        return stringFormatter(getNextWeekDate(currentDate));
    }

}
