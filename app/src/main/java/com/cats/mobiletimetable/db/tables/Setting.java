package com.cats.mobiletimetable.db.tables;

import androidx.room.Entity;
import androidx.room.Index;


@Entity(tableName = "settings", indices = {@Index(value = {"name"}, unique = true)})
public class Setting extends BaseTable {


    public String name;

    public String value;
}
