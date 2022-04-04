package com.cats.mobiletimetable.db.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "buildings")
public class Building {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String label;

}
