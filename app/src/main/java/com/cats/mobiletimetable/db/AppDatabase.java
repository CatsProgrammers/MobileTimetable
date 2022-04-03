package com.cats.mobiletimetable.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cats.mobiletimetable.db.dao.BuildingDao;
import com.cats.mobiletimetable.db.dao.GroupDao;
import com.cats.mobiletimetable.db.dao.KindOfWorkDao;
import com.cats.mobiletimetable.db.dao.LessonDao;
import com.cats.mobiletimetable.db.dao.SettingsDao;
import com.cats.mobiletimetable.db.dao.TeacherDao;
import com.cats.mobiletimetable.db.tables.Building;
import com.cats.mobiletimetable.db.tables.Group;
import com.cats.mobiletimetable.db.tables.KindOfWork;
import com.cats.mobiletimetable.db.tables.Lesson;
import com.cats.mobiletimetable.db.tables.Setting;
import com.cats.mobiletimetable.db.tables.Teacher;


@Database(entities = {Building.class, Group.class, KindOfWork.class, Lesson.class, Teacher.class, Setting.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "db").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public abstract BuildingDao buildingDao();

    public abstract GroupDao groupDao();

    public abstract KindOfWorkDao kindOfWorkDao();

    public abstract LessonDao lessonDao();

    public abstract TeacherDao teacherDao();

    public abstract SettingsDao settingsDao();
}
