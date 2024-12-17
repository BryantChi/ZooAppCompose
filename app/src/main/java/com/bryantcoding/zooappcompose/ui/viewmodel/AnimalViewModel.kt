package com.bryantcoding.zooappcompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bryantcoding.zooappcompose.data.local.entities.AnimalEntity
import com.bryantcoding.zooappcompose.domain.usecase.GetDataUseCase
import com.bryantcoding.zooappcompose.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalViewModel @Inject constructor(
    private val getDataUseCase: GetDataUseCase
): ViewModel() {
    private val _animal = MutableStateFlow<UiState<AnimalEntity>>(UiState.Loading)
    val animal: StateFlow<UiState<AnimalEntity>> = _animal

    fun fetchAnimalDetail(animalID: Int) {
        viewModelScope.launch {
            _animal.emit(UiState.Loading)
            try {
                val result = getDataUseCase.getAnimalDetail(animalID)
                _animal.emit(UiState.Success(result))
            } catch (e: Exception) {
                _animal.emit(UiState.Error(e.message ?: "Unknown error"))
            }
        }
    }
}