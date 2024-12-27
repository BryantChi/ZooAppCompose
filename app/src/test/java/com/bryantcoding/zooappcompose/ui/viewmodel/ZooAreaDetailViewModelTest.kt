package com.bryantcoding.zooappcompose.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.bryantcoding.zooappcompose.data.local.entities.AnimalEntity
import com.bryantcoding.zooappcompose.data.local.entities.ZooAreaEntity
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
class ZooAreaDetailViewModelTest {

    private lateinit var viewModel: ZooAreaDetailViewModel
    private val getDataUseCase: GetDataUseCase = mockk() // 模擬 UseCase
    private val savedStateHandle: SavedStateHandle = mockk() // 模擬 SavedStateHandle
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
    fun `fetchZooAreaDetail with null ID - emits Error state`() = testScope.runTest {

        every { savedStateHandle.get<String>("id") } returns null

        viewModel = ZooAreaDetailViewModel(getDataUseCase, savedStateHandle)

        viewModel.zooAreaDetail.test {
            assertEquals(UiState.Error("Invalid ID"), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchZooAreaDetail success - flow should emit Loading then Success`() = testScope.runTest {
        // Arrange: 模擬成功回傳
        val zooAreaID = "1"
        val mockData = ZooAreaEntity(id = 1, eName = "Area 1", eInfo = "Description 1")
        every { savedStateHandle.get<String>("id") } returns zooAreaID
        coEvery { getDataUseCase.getZooAreaDetail(any()) } returns mockData

        // Act: 初始化 ViewModel
        viewModel = ZooAreaDetailViewModel(getDataUseCase, savedStateHandle)

        // Assert: 測試 StateFlow 狀態
        viewModel.zooAreaDetail.test {
            assertEquals(UiState.Loading, awaitItem()) // Loading 狀態
            assertEquals(UiState.Success(mockData), awaitItem()) // Success 狀態
            cancelAndIgnoreRemainingEvents()
        }

        // Verify: 確保方法被呼叫
        coVerify { getDataUseCase.getZooAreaDetail(any()) }
    }

    @Test
    fun `fetchZooAreaDetail failure - flow should emit Loading then Error`() = testScope.runTest {
        // Arrange: 模擬異常
        val zooAreaID = "1"
        val mockExceptionMessage = "Network Error"
        every { savedStateHandle.get<String>("id") } returns zooAreaID
        coEvery { getDataUseCase.getZooAreaDetail(any()) } throws Exception(mockExceptionMessage)

        // Act: 初始化 ViewModel
        viewModel = ZooAreaDetailViewModel(getDataUseCase, savedStateHandle)

        // Assert: 測試 StateFlow 狀態
        viewModel.zooAreaDetail.test {
            assertEquals(UiState.Loading, awaitItem()) // Loading 狀態
            assertEquals(UiState.Error(mockExceptionMessage), awaitItem()) // Error 狀態
            cancelAndIgnoreRemainingEvents()
        }

        // Verify: 確保方法被呼叫
        coVerify { getDataUseCase.getZooAreaDetail(any()) }
    }

    @Test
    fun `fetchAnimalsList success - flow should emit Loading then Success`() = testScope.runTest {
        // Arrange: 模擬成功回傳
        val mockDataZooArea = ZooAreaEntity(id = 10, eName = "Area 10", eInfo = "Description 10")
        val mockDataAnimals = listOf(
            AnimalEntity(id = 1, nameCh = "Animal 1", nameEn = "Animal 1", location = "Location 1"),
            AnimalEntity(id = 2, nameCh = "Animal 2", nameEn = "Animal 2", location = "Location 2")
        )
        every { savedStateHandle.get<String>("id") } returns "10"
        coEvery {
            getDataUseCase.getZooAreaDetail(mockDataZooArea.id)
        } returns mockDataZooArea
        coEvery {
            getDataUseCase.getAnimalsList(mockDataZooArea.eName)
        } returns mockDataAnimals

        viewModel = ZooAreaDetailViewModel(getDataUseCase, savedStateHandle)

        viewModel.zooAreaDetail.test {
            assertEquals(UiState.Loading, awaitItem())  // 第一次預設 Loading
            assertEquals(UiState.Success(mockDataZooArea), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.animalList.test {
            assertEquals(UiState.Success(mockDataAnimals), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        coVerify {
            getDataUseCase.getZooAreaDetail(mockDataZooArea.id)
            getDataUseCase.getAnimalsList(mockDataZooArea.eName)
        }
    }

    @Test
    fun `fetchAnimalsList failure - flow should emit Loading then Error`() = testScope.runTest {
        val mockExceptionMessage = "Network Error"
        val mockDataZooArea = ZooAreaEntity(id = 10, eName = "Area 10", eInfo = "Description 10")
        every { savedStateHandle.get<String>("id") } returns "10"
        coEvery {
            getDataUseCase.getZooAreaDetail(mockDataZooArea.id)
        } returns mockDataZooArea
        coEvery {
            getDataUseCase.getAnimalsList(mockDataZooArea.eName)
        } throws Exception(mockExceptionMessage)

        viewModel = ZooAreaDetailViewModel(getDataUseCase, savedStateHandle)

        viewModel.zooAreaDetail.test {
            assertEquals(UiState.Loading, awaitItem()) // Loading 狀態
            assertEquals(UiState.Success(mockDataZooArea), awaitItem()) // Success 狀態
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.animalList.test {
            assertEquals(UiState.Error(mockExceptionMessage), awaitItem()) // Error 狀態
            cancelAndIgnoreRemainingEvents()
        }

        coVerify {
            getDataUseCase.getZooAreaDetail(mockDataZooArea.id)
            getDataUseCase.getAnimalsList(mockDataZooArea.eName)
        }
    }

}