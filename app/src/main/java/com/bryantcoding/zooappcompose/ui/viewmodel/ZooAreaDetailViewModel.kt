package com.bryantcoding.zooappcompose.ui.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bryantcoding.zooappcompose.data.local.entities.AnimalEntity
import com.bryantcoding.zooappcompose.data.local.entities.ZooAreaEntity
import com.bryantcoding.zooappcompose.domain.usecase.GetDataUseCase
import com.bryantcoding.zooappcompose.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZooAreaDetailViewModel @Inject constructor(
    private val getDataUseCase: GetDataUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _zooAreaDetail = MutableStateFlow<UiState<ZooAreaEntity>>(UiState.Loading)
    val zooAreaDetail: StateFlow<UiState<ZooAreaEntity>> = _zooAreaDetail

    private val _animalList = MutableStateFlow<UiState<List<AnimalEntity>>>(UiState.Loading)
    val animalList: StateFlow<UiState<List<AnimalEntity>>> = _animalList

    init {
        val zooAreaID = savedStateHandle.get<String>("id")?.toIntOrNull()
        if (zooAreaID != null) {
            fetchZooAreaDetail(zooAreaID)
        } else {
            _zooAreaDetail.value = UiState.Error("Invalid ID")
        }

        viewModelScope.launch {
            _zooAreaDetail.collect { state ->
                if (state is UiState.Success) {
                    fetchAnimalsList(state.data.eName ?: "")
                }
            }
        }
    }


    private fun fetchZooAreaDetail(zooAreaID: Int) {
        viewModelScope.launch {
            try {
                val result = getDataUseCase.getZooAreaDetail(zooAreaID)
                _zooAreaDetail.update { UiState.Success(result) }
            } catch (e: Exception) {
                _zooAreaDetail.update { UiState.Error(e.message ?: "Unknown error") }
            }
        }
    }

    private fun fetchAnimalsList(zooAreaName: String) {
        viewModelScope.launch {
            try {
                val result = getDataUseCase.getAnimalsList(zooAreaName)
                _animalList.update { UiState.Success(result) }
            } catch (e: Exception) {
                _animalList.update { UiState.Error(e.message ?: "Unknown error") }
            }
        }
    }
}