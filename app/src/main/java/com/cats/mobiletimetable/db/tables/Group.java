package com.cats.mobiletimetable.db.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Group {

    @PrimaryKey(autoGenerate = true)
    public Long id;

    public String label;

    public String type;

    public String description;
}
