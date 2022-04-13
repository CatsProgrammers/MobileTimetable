package com.cats.mobiletimetable;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public final static String groupSettingsKey = "currentGroup";

    public final static String userTypeSettingsKey = "currentUserType";

    public final static String teacherSettingsKey = "currentTeacher";

    public final static String timeSyncSettingsKey = "apiTimeSync";

    /**
     * Форматер для API, отдает форматированные даты
     *
     * @param date - дата поступления
     * @return - строка в формате, которое воспринимает внешнее API
     */
    public static String stringFormatter(Date date) {
        return new SimpleDateFormat("yyyy.MM.dd").format(date);
    }

    /**
     * Форматирует заголовки в расписании
     *
     * @param date - строка в одном формате
     * @return - строка в другом формате
     */
    public static String headerDateFormatter(String date) {

        DateTimeFormatter oldFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("EEE, dd MMMM yyyy");

        LocalDate localDate = LocalDate.parse(date, oldFormatter);
        String formattedString = newFormatter.format(localDate);

        return formattedString.substring(0, 1).toUpperCase() + formattedString.substring(1);
    }

    /**
     * Получение даты, которая будет через 7 дней от текущей
     *
     * @param currentDate - дата, от которой считаем
     * @return - дата через 7 дней
     */
    public static Date getNextWeekDate(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        return calendar.getTime();
    }

    /**
     * Получение даты, которая будет через 7 дней от текущей с форматированием
     *
     * @param currentDate - дата, от которой считаем
     * @return - дата через 7 дней
     */
    public static String getNextWeekDateString(Date currentDate) {
        return stringFormatter(getNextWeekDate(currentDate));
    }

    /**
     * Получение форматера для календаря
     *
     * @return
     */
    public static DateTimeFormatter getCalendarFormatter() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

}
