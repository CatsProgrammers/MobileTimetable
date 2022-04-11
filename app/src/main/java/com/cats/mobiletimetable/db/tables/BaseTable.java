package com.cats.mobiletimetable.db.tables;

import androidx.room.PrimaryKey;

public class BaseTable {

    @PrimaryKey(autoGenerate = true)
    public long id;
}
