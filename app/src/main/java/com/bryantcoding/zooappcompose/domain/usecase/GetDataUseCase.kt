package com.bryantcoding.zooappcompose.domain.usecase

import com.bryantcoding.zooappcompose.data.local.entities.AnimalEntity
import com.bryantcoding.zooappcompose.data.local.entities.ZooAreaEntity
import com.bryantcoding.zooappcompose.data.remote.response.AnimalResponse
import com.bryantcoding.zooappcompose.data.remote.response.ZooAreaResponse
import com.bryantcoding.zooappcompose.data.repository.DataRepository
import javax.inject.Inject

class GetDataUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    /**
     * 獲取動物園區域清單
     */
    suspend fun getZooAreasList(): List<ZooAreaEntity> {
        return dataRepository.getZooAreas()
    }

    /**
     * 獲取動物園區域詳情
     */
    suspend fun getZooAreaDetail(zooAreaID: Int): ZooAreaEntity {
        return dataRepository.getZooAreas().find {
            it.id == zooAreaID
        } ?: ZooAreaEntity()
    }

    /**
     * 根據園區名稱篩選動物
     */
    suspend fun getAnimalsList(zooAreaName: String): List<AnimalEntity> {
        return dataRepository.getAnimals().filter {
            it.location == zooAreaName
        }
    }
}