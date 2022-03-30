package com.cats.mobiletimetable.db.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lessons")
public class Lesson {

    @PrimaryKey(autoGenerate = true)
    public Long id;

    public String name;

    public String auditorium;

    @ColumnInfo(name = "begin_lesson")
    public String beginLesson;

    @ColumnInfo(name = "end_lesson")
    public String endLesson;

    public String url;

    @ColumnInfo(name = "teacher_id")
    public Long teacherId;

    @ColumnInfo(name = "building_id")
    public Long buildingId;

    @ColumnInfo(name = "kind_of_work_id")
    public Long kindOfWorkId;
}
