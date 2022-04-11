package com.cats.mobiletimetable.db.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "groups")
public class Group extends BaseTable {

    @ColumnInfo(name = "label_id")
    public String labelId;

    public String name;

    public String type;

    public String description;
}
