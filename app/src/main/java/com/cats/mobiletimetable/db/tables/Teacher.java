package com.cats.mobiletimetable.db.tables;

import androidx.room.Entity;

@Entity(tableName = "teachers")
public class Teacher extends BaseTable {

    public String name;

    public String email;

    public String rank;
}
