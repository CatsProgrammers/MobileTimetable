package com.cats.mobiletimetable.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cats.mobiletimetable.db.tables.Setting;

import java.util.List;

@Dao
public interface SettingsDao {

    @Query("SELECT * FROM settings WHERE id=:id;")
    Setting getItemById(String id);

    @Query("SELECT * FROM settings WHERE name=:name;")
    Setting getItemByName(String name);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertItem(Setting setting);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertItem(Setting... settings);

    @Delete
    void deleteItem(Setting setting);

}
