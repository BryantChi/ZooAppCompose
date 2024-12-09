package com.bryantcoding.zooappcompose.data.repository

import com.bryantcoding.zooappcompose.data.model.AnimalResponse
import com.bryantcoding.zooappcompose.data.model.ZooAreaResponse
import com.bryantcoding.zooappcompose.data.remote.ApiService
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val apiService: ApiService
): BaseRepository {

    /**
     * 獲取動物園區域數據
     * @return [ZooAreaResponse]
     */
    suspend fun getZooAreas(): ZooAreaResponse = safeApiCall {
        apiService.getZooAreas()
    }

    /**
     * 獲取動物數據
     * @return [AnimalResponse]
     */
    suspend fun getAnimals(): AnimalResponse = safeApiCall {
        apiService.getAnimals()
    }

}