package com.cats.mobiletimetable.db.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "buildings")
public class Building {

    @PrimaryKey(autoGenerate = true)
    public Long id;

    public String label;

    public String address;

}
