package com.cats.mobiletimetable.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.cats.mobiletimetable.db.tables.Building;

import java.util.List;

@Dao
public interface BuildingDao {

    @Query("SELECT * FROM buildings;")
    List<Building> getAllBuildings();

    @Insert()
    void insertBuilding(Building... buildings);

    @Delete
    void deleteBuilding(Building building);
}
