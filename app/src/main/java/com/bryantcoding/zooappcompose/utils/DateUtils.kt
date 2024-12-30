package com.bryantcoding.zooappcompose.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtils {

    fun String.formatDateTimeToDate(): String {
        return try {
            // 定義輸入時間格式
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            // 定義輸出日期格式
            val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            // 解析輸入的時間字串
            val dateTime = LocalDateTime.parse(this, inputFormatter)
            // 格式化為指定的輸出格式
            dateTime.format(outputFormatter)
        } catch (e: Exception) {
            // 發生錯誤時，返回空字串或預設值
            ""
        }
    }
}