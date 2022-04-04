package com.cats.mobiletimetable.db.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "groups")
public class Group {

    @PrimaryKey(autoGenerate = true)
    public long groupId;

    @ColumnInfo(name = "label_id")
    public String labelId;

    public String name;

    public String type;

    public String description;
}
