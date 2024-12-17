package com.bryantcoding.zooappcompose.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bryantcoding.zooappcompose.data.remote.response.AnimalResponse
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "animal_table")
data class AnimalEntity(
    @PrimaryKey
    @SerializedName("_id")
    val id: Int = 0, // 動物唯一 ID

    @SerializedName("a_name_ch")
    val nameCh: String? = "", // 中文名稱

    @SerializedName("a_name_en")
    val nameEn: String? = "", // 英文名稱

    @SerializedName("a_summary")
    val summary: String? = "", // 動物簡介

    @SerializedName("a_habitat")
    val habitat: String? = "", // 動物棲息地

    @SerializedName("a_behavior")
    val behavior: String? = "", // 動物行為特徵

    @SerializedName("a_feature")
    val feature: String? = "", // 動物外觀特徵

    @SerializedName("a_pic01_url")
    val pic01Url: String? = "", // 主要圖片 URL

    @SerializedName("a_pic02_url")
    val pic02Url: String? = "", // 次要圖片 URL

    @SerializedName("a_pic03_url")
    val pic03Url: String? = "", // 次要圖片 URL

    @SerializedName("a_pic04_url")
    val pic04Url: String? = "", // 次要圖片 URL

    @SerializedName("a_conservation")
    val conservation: String? = "", // 保育等級

    @SerializedName("a_geo")
    val geo: String? = "", // 動物地理位置

    @SerializedName("a_location")
    val location: String? = "", // 動物分布區域,

    @SerializedName("a_distribution")
    val distribution: String? = "", // 動物分布區域,

    @SerializedName("a_diet")
    val diet: String? = "", // 動物飲食特徵

    @SerializedName("_importdate")
    val importDate: String? = ""
) : Serializable
