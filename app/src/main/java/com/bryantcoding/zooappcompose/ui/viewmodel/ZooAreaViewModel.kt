package com.bryantcoding.zooappcompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bryantcoding.zooappcompose.data.local.entities.ZooAreaEntity
import com.bryantcoding.zooappcompose.domain.usecase.GetDataUseCase
import com.bryantcoding.zooappcompose.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZooAreaViewModel @Inject constructor(
    private val getDataUseCase: GetDataUseCase
) : ViewModel() {
    private val _zooInfo = MutableStateFlow<UiState<List<ZooAreaEntity>>>(UiState.Loading)
    val zooInfo: StateFlow<UiState<List<ZooAreaEntity>>> = _zooInfo

    fun fetchZooAreas() {
        viewModelScope.launch {
            _zooInfo.emit(UiState.Loading)
            try {
                val result = getDataUseCase.getZooAreasList()
                _zooInfo.emit(UiState.Success(result))
            } catch (e: Exception) {
                _zooInfo.emit(UiState.Error(e.message ?: "Unknown error"))
            }
        }
    }
}