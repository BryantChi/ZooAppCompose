package com.bryantcoding.zooappcompose.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bryantcoding.zooappcompose.data.local.entities.AnimalEntity
import com.bryantcoding.zooappcompose.data.local.entities.ZooAreaEntity

@Dao
interface ZooDao {
    // 插入館區數據
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZooAreas(zooAreas: List<ZooAreaEntity>)

    // 插入動物數據
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimals(animals: List<AnimalEntity>)

    // 獲取所有館區數據
    @Query("SELECT * FROM zoo_area_table")
    suspend fun getAllZooAreas(): List<ZooAreaEntity>

    // 獲取所有動物數據
    @Query("SELECT * FROM animal_table")
    suspend fun getAllAnimals(): List<AnimalEntity>

    // 獲取指定館區的動物
    @Query("SELECT * FROM animal_table WHERE location = :zooName")
    suspend fun getAnimalsByZooName(zooName: String): List<AnimalEntity>
}