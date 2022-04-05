package com.cats.mobiletimetable.db.tables;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "settings", indices = {@Index(value = {"name"}, unique = true)})
public class Setting {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;

    public String value;
}
