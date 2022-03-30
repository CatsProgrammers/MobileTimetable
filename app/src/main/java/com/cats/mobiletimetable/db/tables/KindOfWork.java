package com.cats.mobiletimetable.db.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "kinds_of_work")
public class KindOfWork {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public String name;
}