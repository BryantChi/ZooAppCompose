package com.bryantcoding.zooappcompose.data.remote.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ZooAreaResponse(
    val result: Result
): Serializable {
    data class Result(
        val limit: Int? = 0,
        val offset: Int? = 0,
        val count: Int? = 0,
        val sort: String? = "",
        val results: List<ZooArea>? = listOf()
    ): Serializable

    data class ZooArea(
        @SerializedName("_id")
        val id: Int? = 0,
        @SerializedName("e_name")
        val eName: String? = "", // 館區名稱
        @SerializedName("e_pic_url")
        val ePicUrl: String? = "", // 圖片URL
        @SerializedName("e_info")
        val eInfo: String? = "", // 館區描述
        @SerializedName("e_memo")
        val eMemo: String? = "", // 備註
        @SerializedName("e_category")
        val eCategory: String? = "", // 類別
        @SerializedName("e_url")
        val eUrl: String? = "", // 館區相關URL
        @SerializedName("e_geo")
        val eGeo: String? = "", // 地理位置
        @SerializedName("e_no")
        val eNo: String? = "", // 館區編號
        @SerializedName("_importdate")
        val importDate: ImportDate? = ImportDate() // 匯入日期
    ): Serializable

    data class ImportDate(
        @SerializedName("date")
        val date: String? = "", // 日期
        @SerializedName("timezone_type")
        val timezoneType: Int? = 0, // 時區類型
        @SerializedName("timezone")
        val timezone: String? = "" // 時區
    ): Serializable
}
