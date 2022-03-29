package com.cats.mobiletimetable.db.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Teacher {

    @PrimaryKey(autoGenerate = true)
    public Long id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String email;

    @ColumnInfo
    public String rank;
}
