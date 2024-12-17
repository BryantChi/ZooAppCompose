package com.bryantcoding.zooappcompose.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "zoo_area_table")
data class ZooAreaEntity(
    @PrimaryKey
    @SerializedName("_id")
    val id: Int = 0, // 館區唯一 ID

    @SerializedName("e_name")
    val eName: String = "", // 館區名稱

    @SerializedName("e_pic_url")
    val ePicUrl: String = "", // 圖片 URL

    @SerializedName("e_info")
    val eInfo: String = "", // 館區描述

    @SerializedName("e_memo")
    val eMemo: String = "", // 備註

    @SerializedName("e_category")
    val eCategory: String = "", // 館區分類

    @SerializedName("e_url")
    val eUrl: String = "", // 館區相關連結

    @SerializedName("e_geo")
    val eGeo: String = "", // 館區地理位置

    @SerializedName("e_no")
    val eNo: String = "", // 館區編號

) : Serializable
