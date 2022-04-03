package com.cats.mobiletimetable.api;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cats.mobiletimetable.db.dao.BuildingDao;
import com.cats.mobiletimetable.db.dao.GroupDao;
import com.cats.mobiletimetable.db.dao.KindOfWorkDao;
import com.cats.mobiletimetable.db.dao.LessonDao;
import com.cats.mobiletimetable.db.dao.TeacherDao;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public abstract class AppApi {

    private final static String apiBaseUrl = "https://ruz.fa.ru";

    private static TimetableAPI INSTANCE;

    public static TimetableAPI getApiInstance(Context context) {

        if (INSTANCE == null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(apiBaseUrl).addConverterFactory(GsonConverterFactory.create()).build();
            INSTANCE = retrofit.create(TimetableAPI.class);
        }
        return INSTANCE;
    }
}