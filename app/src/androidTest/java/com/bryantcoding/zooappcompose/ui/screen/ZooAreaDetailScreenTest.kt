package com.bryantcoding.zooappcompose.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bryantcoding.zooappcompose.R
import com.bryantcoding.zooappcompose.data.local.entities.AnimalEntity
import com.bryantcoding.zooappcompose.data.local.entities.ZooAreaEntity
import com.bryantcoding.zooappcompose.utils.UiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ZooAreaDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun zooAreaDetailScreen_whenLoading_showsLoading() {
        composeTestRule.setContent {
            ZooAreaDetailScreen(
                navController = rememberNavController(),
                zooAreaDetail = UiState.Loading,
                animalListState = UiState.Loading
            )
        }

        // 同樣假設有 Loading 的文字或組件
        composeTestRule.onNodeWithText("Loading...").assertIsDisplayed()
    }

    @Test
    fun zooAreaDetailScreen_whenSuccess_showsAreaInfo() {
        val mockArea = ZooAreaEntity(eName = "Asian Tropical Rainforest", eInfo = "A vast area...")
        composeTestRule.setContent {
            ZooAreaDetailScreen(
                navController = rememberNavController(),
                zooAreaDetail = UiState.Success(mockArea),
                animalListState = UiState.Success(emptyList())
            )
        }

        // 驗證地區名稱與資訊是否出現在畫面中
        composeTestRule.onNodeWithText("Asian Tropical Rainforest").assertIsDisplayed()
        composeTestRule.onNodeWithText("A vast area...").assertIsDisplayed()
    }

    @Test
    fun zooAreaDetailScreen_whenNoAnimals_showsNoAnimalText() {
        val mockArea = ZooAreaEntity(eName = "Asian Tropical Rainforest", eInfo = "A vast area...")

        composeTestRule.setContent {
            ZooAreaDetailScreen(
                navController = rememberNavController(),
                zooAreaDetail = UiState.Success(mockArea),
                animalListState = UiState.Success(emptyList())
            )
        }

        composeTestRule.onNodeWithText("尚無動物資訊").assertIsDisplayed()
    }

    @Test
    fun zooAreaDetailScreen_whenHasAnimals_showsAnimalList() {
        val mockArea = ZooAreaEntity(eName = "Asian Tropical Rainforest", eInfo = "A vast area...")
        val mockAnimals = listOf(
            AnimalEntity(id = 101, nameCh = "亞洲象", feature = "大型動物"),
            AnimalEntity(id = 102, nameCh = "孟加拉虎", feature = "稀有的大型貓科")
        )

        composeTestRule.setContent {
            ZooAreaDetailScreen(
                navController = rememberNavController(),
                zooAreaDetail = UiState.Success(mockArea),
                animalListState = UiState.Success(mockAnimals)
            )
        }

        composeTestRule.onRoot().printToLog("ZooAreaDetailScreenTest")
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("亞洲象").assertIsDisplayed()
        composeTestRule.onNodeWithText("孟加拉虎").assertIsDisplayed()
    }
}