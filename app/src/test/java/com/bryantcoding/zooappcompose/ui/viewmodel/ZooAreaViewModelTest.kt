package com.bryantcoding.zooappcompose.ui.viewmodel

import app.cash.turbine.test
import com.bryantcoding.zooappcompose.data.local.entities.ZooAreaEntity
import com.bryantcoding.zooappcompose.domain.usecase.GetDataUseCase
import com.bryantcoding.zooappcompose.utils.UiState
import io.mockk.coEvery
import io.mockk.coVerify
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
class ZooAreaViewModelTest {

    private lateinit var viewModel: ZooAreaViewModel
    private val getDataUseCase: GetDataUseCase = mockk() // 模擬依賴
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
    fun viewModelInitializesWithDefaultState() {
        // Act: 初始化 ViewModel
        viewModel = ZooAreaViewModel(getDataUseCase)

        // Assert: 默認狀態是否為 Loading
        assertEquals(UiState.Loading, viewModel.zooInfo.value)
    }

    @Test
    fun fetchZooAreasEmptyListFlowShouldEmitLoadingThenSuccessWithEmptyData() = testScope.runTest {
        val mockData = emptyList<ZooAreaEntity>()
        coEvery { getDataUseCase.getZooAreasList() } returns mockData

        viewModel = ZooAreaViewModel(getDataUseCase)

        viewModel.zooInfo.test {
            assertEquals(UiState.Loading, awaitItem()) // Loading 狀態
            assertEquals(UiState.Success(mockData), awaitItem()) // 成功但數據為空
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { getDataUseCase.getZooAreasList() }
    }

    @Test
    fun fetchZooAreasSuccessFlowShouldEmitLoadingThenSuccess() = testScope.runTest {
        // Arrange: 模擬成功回傳
        val mockData = listOf(
            ZooAreaEntity(id = 1, eName = "Area 1", eInfo = "Description 1"),
            ZooAreaEntity(id = 2, eName = "Area 2", eInfo = "Description 2")
        )
        coEvery { getDataUseCase.getZooAreasList() } returns mockData

        // Act: 初始化 ViewModel
        viewModel = ZooAreaViewModel(getDataUseCase)

        // Assert: 測試 StateFlow 狀態
        viewModel.zooInfo.test {
            assertEquals(UiState.Loading, awaitItem()) // Loading 狀態
            assertEquals(UiState.Success(mockData), awaitItem()) // Success 狀態
            cancelAndIgnoreRemainingEvents()
        }

        // Verify: 確保方法被呼叫
        coVerify { getDataUseCase.getZooAreasList() }
    }

    @Test
    fun fetchZooAreasFailureFlowShouldEmitLoadingThenError() = testScope.runTest {
        // Arrange: 模擬異常
        val mockExceptionMessage = "Network Error"
        coEvery { getDataUseCase.getZooAreasList() } throws Exception(mockExceptionMessage)

        // Act: 初始化 ViewModel
        viewModel = ZooAreaViewModel(getDataUseCase)

        // Assert: 測試 StateFlow 狀態
        viewModel.zooInfo.test {
            assertEquals(UiState.Loading, awaitItem()) // Loading 狀態
            assertEquals(UiState.Error(mockExceptionMessage), awaitItem()) // Error 狀態
            cancelAndIgnoreRemainingEvents()
        }

        // Verify: 確保方法被呼叫
        coVerify { getDataUseCase.getZooAreasList() }
    }
}