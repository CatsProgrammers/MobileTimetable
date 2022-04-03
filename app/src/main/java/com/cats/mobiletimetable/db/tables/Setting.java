package com.cats.mobiletimetable.db.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "settings")
public class Setting {

    @PrimaryKey(autoGenerate = true)
    public Long id;

    public String name;

    public String value;
}
