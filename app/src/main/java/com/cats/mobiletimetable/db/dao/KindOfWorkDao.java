package com.cats.mobiletimetable.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cats.mobiletimetable.db.tables.KindOfWork;

import java.util.List;

@Dao
public interface KindOfWorkDao {

    @Query("SELECT * FROM kinds_of_work;")
    List<KindOfWork> getAllItems();

    @Query("SELECT * FROM kinds_of_work WHERE id=:id;")
    KindOfWork getItemById(String id);


    @Query("SELECT * FROM kinds_of_work WHERE name=:name;")
    KindOfWork getItemByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertItem(KindOfWork kindOfWork);

    // Insert multiple items
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertItem(KindOfWork... kindOfWorks);

    @Delete
    void deleteItem(KindOfWork kindOfWork);


}
