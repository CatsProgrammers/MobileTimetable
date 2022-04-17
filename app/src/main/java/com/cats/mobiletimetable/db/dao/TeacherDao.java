package com.cats.mobiletimetable.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cats.mobiletimetable.db.tables.Teacher;

import java.util.List;

@Dao
public interface TeacherDao {

    @Query("SELECT * FROM teachers;")
    List<Teacher> getAllTeachers();

    @Query("SELECT * FROM teachers WHERE id=:id;")
    Teacher getTeacherById(String id);

    @Query("SELECT * FROM teachers WHERE name=:name;")
    Teacher getTeacherByName(String name);

    @Update
    void updateTeacher(Teacher teacher);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertTeacher(Teacher teacher);

    // Insert multiple items
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertTeacher(Teacher... teachers);

    @Delete
    void deleteTeacher(Teacher teacher);
}
