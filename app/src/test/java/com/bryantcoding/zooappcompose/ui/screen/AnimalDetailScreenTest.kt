package com.bryantcoding.zooappcompose.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.compose.rememberNavController
import com.bryantcoding.zooappcompose.data.local.entities.AnimalEntity
import com.bryantcoding.zooappcompose.utils.UiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

//@RunWith(AndroidJUnit4::class)
@Config(sdk = [33]) // 指定使用 Robolectric 支援的最高 SDK 版本
@RunWith(RobolectricTestRunner::class)
class AnimalDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun animalDetailScreen_whenLoading_showsLoading() {
        composeTestRule.setContent {
            AnimalDetailScreen(
                navController = rememberNavController(),
                animalDetail = UiState.Loading
            )
        }

        composeTestRule.onNodeWithText("Loading...").assertIsDisplayed()
    }

    @Test
    fun animalDetailScreen_whenError_showsErrorText() {
        val errorMessage = "Error loading animal"
        composeTestRule.setContent {
            AnimalDetailScreen(
                navController = rememberNavController(),
                animalDetail = UiState.Error(errorMessage)
            )
        }

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun animalDetailScreen_whenSuccess_showsAnimalInfo() {
        val mockAnimal = AnimalEntity(
            id = 101,
            nameCh = "大熊貓",
            nameEn = "Giant Panda",
            feature = "黑白相間的毛色",
            distribution = "中國四川"
        )

        composeTestRule.setContent {
            AnimalDetailScreen(
                navController = rememberNavController(),
                animalDetail = UiState.Success(mockAnimal)
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("animal_name_ch").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithTag("animal_name_en").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithTag("animal_feature").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithTag("animal_distribution").performScrollTo().assertIsDisplayed()
    }
}