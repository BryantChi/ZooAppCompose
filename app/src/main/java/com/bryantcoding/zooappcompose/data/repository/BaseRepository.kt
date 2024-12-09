package com.bryantcoding.zooappcompose.data.repository

interface BaseRepository {
    /**
     * 通用的 API 調用安全處理函數
     * @param apiCall Lambda 表達式進行 API 調用
     * @return 泛型類型的結果
     */
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): T {
        return try {
            apiCall()
        } catch (exception: Exception) {
            throw handleException(exception)
        }
    }

    /**
     * 通用的錯誤處理邏輯
     * @param exception 捕獲的異常
     * @return 處理後的異常類型
     */
    private fun handleException(exception: Exception): Exception {
        // 可擴展的異常處理邏輯，例如網絡錯誤、自定義異常等
        return when (exception) {
            is java.net.UnknownHostException -> Exception("Network error: Unable to connect")
            is retrofit2.HttpException -> Exception("HTTP error: ${exception.code()}")
            else -> Exception("Unknown error occurred")
        }
    }
}