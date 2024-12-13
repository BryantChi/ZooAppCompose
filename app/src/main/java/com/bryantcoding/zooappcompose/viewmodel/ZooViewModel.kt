package com.bryantcoding.zooappcompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bryantcoding.zooappcompose.data.model.AnimalResponse
import com.bryantcoding.zooappcompose.data.model.ZooAreaResponse
import com.bryantcoding.zooappcompose.domain.usecase.GetDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZooAreaViewModel @Inject constructor(
    private val getDataUseCase: GetDataUseCase
): ViewModel() {
    private val _zooInfo = MutableStateFlow<UiState<List<ZooAreaResponse.ZooArea>>>(UiState.Loading)
    val zooInfo: StateFlow<UiState<List<ZooAreaResponse.ZooArea>>> = _zooInfo

    private val _zooAreaDetail = MutableStateFlow<UiState<ZooAreaResponse.ZooArea>>(UiState.Loading)
    val zooAreaDetail: StateFlow<UiState<ZooAreaResponse.ZooArea>> = _zooAreaDetail

    private val _animalList = MutableStateFlow<UiState<List<AnimalResponse.Animal>>>(UiState.Loading)
    val animalList: StateFlow<UiState<List<AnimalResponse.Animal>>> = _animalList

    private val _selectedAnimal = MutableStateFlow<AnimalResponse.Animal?>(null)
    val selectedAnimal: StateFlow<AnimalResponse.Animal?> = _selectedAnimal

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

    fun fetchZooAreaDetail(zooAreaID: Int) {
        viewModelScope.launch {
            _zooAreaDetail.emit(UiState.Loading)
            try {
                val result = getDataUseCase.getZooAreaDetail(zooAreaID)
                _zooAreaDetail.emit(UiState.Success(result))
            } catch (e: Exception) {
                _zooAreaDetail.emit(UiState.Error(e.message ?: "Unknown error"))
            }
        }
    }

    fun fetchAnimalsList(zooAreaName: String) {
        viewModelScope.launch {
            _animalList.emit(UiState.Loading)
            try {
                val result = getDataUseCase.getAnimalsList(zooAreaName)
                _animalList.emit(UiState.Success(result))
            } catch (e: Exception) {
                _animalList.emit(UiState.Error(e.message ?: "Unknown error"))
            }
        }
    }

    fun setSelectedAnimal(animal: AnimalResponse.Animal) {
        viewModelScope.launch {
            _selectedAnimal.emit(animal)
        }
    }
}

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}