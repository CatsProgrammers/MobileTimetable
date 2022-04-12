package com.cats.mobiletimetable.db.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "lessons")
public class Lesson extends BaseTable {

    public String name;

    public String auditorium;

    @ColumnInfo(name = "begin_lesson")
    public String beginLesson;

    @ColumnInfo(name = "end_lesson")
    public String endLesson;

    public String url;

    public String stream;

    public String date;

    @ColumnInfo(name = "teacher_id")
    public long teacherId;

    @ColumnInfo(name = "building_id")
    public long buildingId;

    @ColumnInfo(name = "kind_of_work_id")
    public long kindOfWorkId;
}
