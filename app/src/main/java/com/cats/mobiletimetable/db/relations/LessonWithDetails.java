package com.cats.mobiletimetable.db.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.cats.mobiletimetable.db.tables.Building;
import com.cats.mobiletimetable.db.tables.KindOfWork;
import com.cats.mobiletimetable.db.tables.Lesson;
import com.cats.mobiletimetable.db.tables.Teacher;


public class LessonWithDetails {
    @Embedded
    public Lesson lesson;

    @Relation(
            parentColumn = "teacher_id",
            entityColumn = "id"
    )
    public Teacher teacher;


    @Relation(
            parentColumn = "building_id",
            entityColumn = "id"
    )
    public Building building;


    @Relation(
            parentColumn = "kind_of_work_id",
            entityColumn = "id"
    )
    public KindOfWork kindOfWork;
}