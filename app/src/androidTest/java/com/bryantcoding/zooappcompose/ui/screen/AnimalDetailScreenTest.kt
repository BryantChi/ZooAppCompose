package com.bryantcoding.zooappcompose.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bryantcoding.zooappcompose.data.local.entities.AnimalEntity
import com.bryantcoding.zooappcompose.utils.UiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
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

        composeTestRule.onNodeWithTag("animal_name_ch").assertIsDisplayed()
        composeTestRule.onNodeWithTag("animal_name_en").assertIsDisplayed()
        composeTestRule.onNodeWithTag("animal_feature").assertIsDisplayed()
        composeTestRule.onNodeWithTag("animal_distribution").assertIsDisplayed()
    }
}