package com.bryantcoding.zooappcompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bryantcoding.zooappcompose.data.local.entities.AnimalEntity
import com.bryantcoding.zooappcompose.data.local.entities.ZooAreaEntity
import com.bryantcoding.zooappcompose.domain.usecase.GetDataUseCase
import com.bryantcoding.zooappcompose.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZooAreaDetailViewModel @Inject constructor(
    private val getDataUseCase: GetDataUseCase
): ViewModel() {
    private val _zooAreaDetail = MutableStateFlow<UiState<ZooAreaEntity>>(UiState.Loading)
    val zooAreaDetail: StateFlow<UiState<ZooAreaEntity>> = _zooAreaDetail

    private val _animalList = MutableStateFlow<UiState<List<AnimalEntity>>>(UiState.Loading)
    val animalList: StateFlow<UiState<List<AnimalEntity>>> = _animalList

    private val _selectedAnimal = MutableStateFlow<AnimalEntity?>(null)
    val selectedAnimal: StateFlow<AnimalEntity?> = _selectedAnimal

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

    fun setSelectedAnimal(animal: AnimalEntity) {
        viewModelScope.launch {
            _selectedAnimal.emit(animal)
        }
    }
}