package com.cats.mobiletimetable.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cats.mobiletimetable.db.tables.Building;

import java.util.List;

@Dao
public interface BuildingDao {

    @Query("SELECT * FROM buildings;")
    List<Building> getAllBuildings();


    @Query("SELECT * FROM buildings WHERE id=:id;")
    Building getBuildingById(String id);

    @Query("SELECT * FROM buildings WHERE label=:label;")
    Building getBuildingByLabel(String label);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertBuilding(Building building);

    // Insert multiple items
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertBuilding(Building... buildings);

    @Delete
    void deleteBuilding(Building building);
}
