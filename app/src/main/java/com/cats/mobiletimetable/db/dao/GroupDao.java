package com.cats.mobiletimetable.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cats.mobiletimetable.db.tables.Building;
import com.cats.mobiletimetable.db.tables.Group;
import com.cats.mobiletimetable.db.tables.Setting;

import java.util.List;

@Dao
public interface GroupDao {

    @Query("SELECT * FROM groups;")
    List<Group> getAllGroups();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertGroup(Group group);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertGroup(Group... groups);


    @Delete
    void deleteGroup(Group group);
}
