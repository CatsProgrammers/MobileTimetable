package com.cats.mobiletimetable.db.tables;

import androidx.room.Entity;

@Entity(tableName = "buildings")
public class Building extends BaseTable {

    public String label;

}
