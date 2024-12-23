package com.bryantcoding.zooappcompose.utils

import com.bryantcoding.zooappcompose.utils.DateUtils.formatDateTimeToDate
import org.junit.Assert.assertEquals
import org.junit.Test

class DateUtilsTest {

    @Test
    fun testFormatDateTimeToDate_validInput() {
        // 測試用的輸入字串
        val inputDateTime = "2024-12-23 15:30:45"
        // 預期的輸出結果
        val expectedDate = "2024-12-23"

        // 驗證輸出結果是否符合預期
        val actualDate = inputDateTime.formatDateTimeToDate()
        assertEquals(expectedDate, actualDate)
    }

    @Test
    fun testFormatDateTimeToDate_invalidInput() {
        // 測試用的無效輸入字串
        val invalidInput = "invalid_date"
        // 預期的輸出結果為空字串
        val expectedOutput = ""

        // 驗證無效輸入時的行為
        val actualOutput = invalidInput.formatDateTimeToDate()
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun testFormatDateTimeToDate_emptyInput() {
        // 測試用的空字串
        val emptyInput = ""
        // 預期的輸出結果為空字串
        val expectedOutput = ""

        // 驗證空字串輸入時的行為
        val actualOutput = emptyInput.formatDateTimeToDate()
        assertEquals(expectedOutput, actualOutput)
    }

}