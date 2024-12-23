package com.bryantcoding.zooappcompose.ui.screen

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bryantcoding.zooappcompose.data.local.entities.ZooAreaEntity
import com.bryantcoding.zooappcompose.utils.UiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

//@RunWith(AndroidJUnit4::class)
@Config(sdk = [33]) // 指定使用 Robolectric 支援的最高 SDK 版本
@RunWith(RobolectricTestRunner::class)
class ZooAreasScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testZooAreasScreenLoadingState() {

        composeTestRule.setContent {
            ZooAreasScreen(
                navController = rememberNavController(),
                zooAreas = UiState.Loading
            )
        }

        composeTestRule.onNodeWithText("Loading...").assertIsDisplayed()

    }

    @Test
    fun testZooAreasScreenSuccessState() {
        val mockData = listOf(
            ZooAreaEntity(
                id = 1,
                eName = "Zoo Area 1",
                eCategory = "Category 1",
                eInfo = "Info 1",
                ePicUrl = "https://example.com/image1.jpg",
                eUrl = "https://example.com/area1",
                eMemo = "Memo 1",
                eGeo = "Geo 1",
                eNo = "No 1",
            ),
            ZooAreaEntity(
                id = 2,
                eName = "Zoo Area 2",
                eCategory = "Category 2",
                eInfo = "Info 2",
                ePicUrl = "https://example.com/image2.jpg",
                eUrl = "https://example.com/area2",
                eMemo = "Memo 2",
                eGeo = "Geo 2",
                eNo = "No 2",
            )
        )

        composeTestRule.setContent {
            ZooAreasScreen(
                navController = rememberNavController(),
                zooAreas = UiState.Success(mockData)
            )
        }

        composeTestRule.onNodeWithText("Zoo Area 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Zoo Area 2").assertIsDisplayed()
    }

    @Test
    fun testZooAreasScreenErrorState() {
        composeTestRule.setContent {
            ZooAreasScreen(
                navController = rememberNavController(),
                zooAreas = UiState.Error("Error")
            )
        }

        composeTestRule.onNodeWithText("Error").assertIsDisplayed()
    }
}