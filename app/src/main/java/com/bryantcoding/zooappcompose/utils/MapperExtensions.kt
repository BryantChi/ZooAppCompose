package com.bryantcoding.zooappcompose.utils

import com.bryantcoding.zooappcompose.data.local.entities.AnimalEntity
import com.bryantcoding.zooappcompose.data.local.entities.ZooAreaEntity
import com.bryantcoding.zooappcompose.data.remote.response.AnimalResponse
import com.bryantcoding.zooappcompose.data.remote.response.ZooAreaResponse

fun ZooAreaResponse.ZooArea.toEntity() = ZooAreaEntity(
    id = id ?: -1,
    eName = eName ?: "",
    eInfo = eInfo ?: "",
    ePicUrl = ePicUrl ?: "",
    eCategory = eCategory ?: "",
    eUrl = eUrl ?: "",
    eMemo = eMemo ?: "",
    eGeo = eGeo ?: "",
    eNo = eNo ?: ""
)

fun AnimalResponse.Animal.toEntity() = AnimalEntity(
    id = id ?: -1,
    nameCh = nameCh ?: "",
    nameEn = nameEn ?: "",
    location = location ?: "",
    feature = feature ?: "",
    pic01Url = pic01Url ?: "",
    pic02Url = pic02Url ?: "",
    pic03Url = pic03Url ?: "",
    pic04Url = pic04Url ?: "",
    summary = summary ?: "",
    behavior = behavior ?: "",
    habitat = habitat ?: "",
    conservation = conservation ?: "",
    geo = geo ?: "",
    distribution = distribution ?: "",
    diet = diet ?: "",
    importDate = importDate?.date ?: "",
)