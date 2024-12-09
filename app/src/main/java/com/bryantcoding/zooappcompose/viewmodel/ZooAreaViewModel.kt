package com.bryantcoding.zooappcompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bryantcoding.zooappcompose.data.model.ZooAreaResponse
import com.bryantcoding.zooappcompose.data.repository.DataRepository
import com.bryantcoding.zooappcompose.domain.usecase.DataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.withTimeout
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class ZooAreaViewModel @Inject constructor(
    private val dataUseCase: DataUseCase
): ViewModel() {
    private val _zooInfoStatus = MutableStateFlow<UiState<List<ZooAreaResponse.ZooArea>>>(UiState.Loading)
    val zooInfoStatus: StateFlow<UiState<List<ZooAreaResponse.ZooArea>>> = _zooInfoStatus

    fun fetchZooAreas() {
        viewModelScope.launch {
            _zooInfoStatus.value = UiState.Loading
            try {
                val result = dataUseCase.getZooAreasList()
                _zooInfoStatus.value = UiState.Success(result)
            } catch (e: Exception) {
                _zooInfoStatus.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}