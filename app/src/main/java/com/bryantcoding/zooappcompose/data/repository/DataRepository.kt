package com.bryantcoding.zooappcompose.data.repository

import com.bryantcoding.zooappcompose.data.local.db.ZooDao
import com.bryantcoding.zooappcompose.data.local.entities.AnimalEntity
import com.bryantcoding.zooappcompose.data.local.entities.ZooAreaEntity
import com.bryantcoding.zooappcompose.data.remote.response.AnimalResponse
import com.bryantcoding.zooappcompose.data.remote.response.ZooAreaResponse
import com.bryantcoding.zooappcompose.data.remote.ApiService
import com.bryantcoding.zooappcompose.utils.toEntity
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val apiService: ApiService,
    private val zooDao: ZooDao
): BaseRepository {

    /**
     * 獲取動物園區域數據
     * @return [ZooAreaResponse]
     */
    suspend fun getZooAreas(): List<ZooAreaEntity> = safeApiCall {
        val response = apiService.getZooAreas()
        val entities = response.result.results?.map { it.toEntity() } ?: emptyList()
        zooDao.insertZooAreas(entities)
        zooDao.getAllZooAreas()
    }

    /**
     * 獲取動物數據
     * @return [AnimalResponse]
     */
    suspend fun getAnimals(): List<AnimalEntity> = safeApiCall {
        val response = apiService.getAnimals()
        val entities = response.result.results?.map { it.toEntity()} ?: emptyList()
        zooDao.insertAnimals(entities)
        zooDao.getAllAnimals()
    }

}