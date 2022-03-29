package com.cats.mobiletimetable.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.cats.mobiletimetable.db.tables.KindOfWork;

import java.util.List;

@Dao
public interface KindOfWorkDao {

    @Query("SELECT * FROM kinds_of_work;")
    List<KindOfWork> getAllItems();

    @Insert()
    void insertItem(KindOfWork... kindOfWorks);

    @Delete
    void deleteItem(KindOfWork kindOfWork);
}
