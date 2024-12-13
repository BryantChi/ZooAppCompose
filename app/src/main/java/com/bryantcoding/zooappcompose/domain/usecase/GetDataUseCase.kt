package com.bryantcoding.zooappcompose.domain.usecase

import com.bryantcoding.zooappcompose.data.model.AnimalResponse
import com.bryantcoding.zooappcompose.data.model.ZooAreaResponse
import com.bryantcoding.zooappcompose.data.repository.DataRepository
import javax.inject.Inject

class GetDataUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    /**
     * 獲取動物園區域清單
     */
    suspend fun getZooAreasList(): List<ZooAreaResponse.ZooArea> {
        return dataRepository.getZooAreas().result.results ?: emptyList()
    }

    /**
     * 獲取動物園區域詳情
     */
    suspend fun getZooAreaDetail(zooAreaID: Int): ZooAreaResponse.ZooArea {
        return dataRepository.getZooAreas().result.results?.find {
            it.id == zooAreaID
        }?: ZooAreaResponse.ZooArea()
    }

    /**
     * 根據園區名稱篩選動物
     */
    suspend fun getAnimalsList(zooAreaName: String): List<AnimalResponse.Animal> {
        return dataRepository.getAnimals().result.results?.filter {
            it.location == zooAreaName
        } ?: emptyList()
    }
}