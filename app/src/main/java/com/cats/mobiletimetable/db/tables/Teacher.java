package com.cats.mobiletimetable.db.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "teachers")
public class Teacher {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;

    public String email;

    public String rank;
}
