package com.bryantcoding.zooappcompose.data.remote

import com.bryantcoding.zooappcompose.data.model.AnimalResponse
import com.bryantcoding.zooappcompose.data.model.ZooAreaResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val DEFAULT_SCOPE = "resourceAquire"
    }

    /**
     * 臺北市立動物園_館區簡介 API
     * @param scope 默認為 "resourceAquire"，可以覆蓋以支持其他查詢範圍。
     * @return [ZooAreaResponse]
     */
    @GET("api/v1/dataset/9683ba26-109e-4cb8-8f3d-03d1b349db9f")
    suspend fun getZooAreas(
        @Query("scope") scope: String = DEFAULT_SCOPE
    ): ZooAreaResponse

    /**
    * 臺北市立動物園_動物簡介 API
    * @param scope 默認為 "resourceAquire"，可以覆蓋以支持其他查詢範圍。
    * @return [AnimalResponse]
    */
    @GET("api/v1/dataset/6afa114d-38a2-4e3c-9cfd-29d3bd26b65b")
    suspend fun getAnimals(
        @Query("scope") scope: String = DEFAULT_SCOPE
    ): AnimalResponse

}