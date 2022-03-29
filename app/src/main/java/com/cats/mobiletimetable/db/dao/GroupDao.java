package com.cats.mobiletimetable.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.cats.mobiletimetable.db.tables.Group;

import java.util.List;

@Dao
public interface GroupDao {

    @Query("SELECT * FROM groups;")
    List<Group> getAllGroups();

    @Insert()
    void insertGroup(Group... groups);

    @Delete
    void deleteGroup(Group group);
}
