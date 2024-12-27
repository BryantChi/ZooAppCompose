package com.bryantcoding.zooappcompose.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.bryantcoding.zooappcompose.data.local.entities.AnimalEntity
import com.bryantcoding.zooappcompose.domain.usecase.GetDataUseCase
import com.bryantcoding.zooappcompose.utils.UiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AnimalViewModelTest {

    private lateinit var viewModel: AnimalViewModel
    private val getDataUseCase: GetDataUseCase = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun fetchAnimalDetailEmptyData() = testScope.runTest {
        val animalID = 1
        every { savedStateHandle.get<String>("id") } returns animalID.toString()
        coEvery {
            getDataUseCase.getAnimalDetail(animalID)
        } returns AnimalEntity()

        viewModel = AnimalViewModel(getDataUseCase, savedStateHandle)

        viewModel.animal.test {
            assertEquals(UiState.Loading, awaitItem()) // Loading 狀態
            assertEquals(UiState.Success(AnimalEntity()), awaitItem()) // Success 狀態
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { getDataUseCase.getAnimalDetail(animalID) }
    }

    @Test
    fun fetchAnimalDetailInvalidID() = testScope.runTest {
        // Arrange: 模擬 ID 不存在
        every { savedStateHandle.get<String>("id") } returns null

        viewModel = AnimalViewModel(getDataUseCase, savedStateHandle)

        viewModel.animal.test {
            assertEquals(UiState.Error("Invalid ID"), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        // Assert: 確保 UseCase 未被呼叫
        coVerify(exactly = 0) { getDataUseCase.getAnimalDetail(any()) }
    }

    @Test
    fun fetchAnimalDetailNetworkError() = testScope.runTest {
        val animalID = 1
        every { savedStateHandle.get<String>("id") } returns animalID.toString()
        coEvery {
            getDataUseCase.getAnimalDetail(animalID)
        } throws Exception("Network Error")

        viewModel = AnimalViewModel(getDataUseCase, savedStateHandle)

        viewModel.animal.test {
            assertEquals(UiState.Loading, awaitItem())
            assertEquals(UiState.Error("Network Error"), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { getDataUseCase.getAnimalDetail(animalID) }
    }

    @Test
    fun fetchAnimalDetailSuccess() = testScope.runTest {
        // Arrange: 模擬成功回傳
        val animalID = 1
        val mockData = AnimalEntity(id = 1, nameCh = "Animal 1", nameEn = "Animal 1", location = "Location 1")
        every { savedStateHandle.get<String>("id") } returns animalID.toString()
        coEvery {
            getDataUseCase.getAnimalDetail(animalID)
        } returns mockData

        viewModel = AnimalViewModel(getDataUseCase, savedStateHandle)

        viewModel.animal.test {
            assertEquals(UiState.Loading, awaitItem()) // Loading 狀態
            assertEquals(UiState.Success(mockData), awaitItem()) // Success 狀態
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { getDataUseCase.getAnimalDetail(animalID) }
    }

    @Test
    fun fetchAnimalDetailError() = testScope.runTest {
        // Arrange: 模擬錯誤回傳
        val animalID = 1
        val errorMessage = "Failed to fetch animal detail"
        every { savedStateHandle.get<String>("id") } returns animalID.toString()
        coEvery {
            getDataUseCase.getAnimalDetail(animalID)
        } throws Exception(errorMessage)

        viewModel = AnimalViewModel(getDataUseCase, savedStateHandle)

        viewModel.animal.test {
            assertEquals(UiState.Loading, awaitItem()) // Loading 狀態
            assertEquals(UiState.Error(errorMessage), awaitItem()) // Error 狀態
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { getDataUseCase.getAnimalDetail(animalID) }
    }
}