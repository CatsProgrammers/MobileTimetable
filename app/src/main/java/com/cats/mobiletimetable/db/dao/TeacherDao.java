package com.cats.mobiletimetable.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.cats.mobiletimetable.db.tables.Teacher;

import java.util.List;

@Dao
public interface TeacherDao {

    @Query("SELECT * FROM teachers;")
    List<Teacher> getAllTeachers();

    @Insert()
    void insertTeacher(Teacher... teachers);

    @Delete
    void deleteTeacher(Teacher teacher);
}
