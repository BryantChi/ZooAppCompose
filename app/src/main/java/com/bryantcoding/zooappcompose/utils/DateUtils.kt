package com.bryantcoding.zooappcompose.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateUtils {

    fun formatDateTimeToDate(input: String): String {
        return try {
            // 定義輸入格式
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            // 定義輸出格式
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            // 解析輸入的日期時間
            val date = inputFormat.parse(input)
            // 格式化為指定的輸出格式
            date?.let { outputFormat.format(it) } ?: ""
        } catch (e: Exception) {
            // 發生錯誤時，返回空字串或預設值
            ""
        }
    }
}