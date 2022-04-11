package com.cats.mobiletimetable.db.tables;

import androidx.room.Entity;

@Entity(tableName = "kinds_of_work")
public class KindOfWork extends BaseTable {

    public String name;
}
