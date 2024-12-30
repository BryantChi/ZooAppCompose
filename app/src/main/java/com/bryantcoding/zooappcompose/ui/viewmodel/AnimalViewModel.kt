package com.bryantcoding.zooappcompose.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bryantcoding.zooappcompose.data.local.entities.AnimalEntity
import com.bryantcoding.zooappcompose.domain.usecase.GetDataUseCase
import com.bryantcoding.zooappcompose.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalViewModel @Inject constructor(
    private val getDataUseCase: GetDataUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _animal = MutableStateFlow<UiState<AnimalEntity>>(UiState.Loading)
    val animal: StateFlow<UiState<AnimalEntity>> = _animal

    init {
        val id = savedStateHandle.get<String>("id")?.toIntOrNull()
        if (id != null) {
            fetchAnimalDetail(id)
        } else {
            _animal.update {  UiState.Error("Invalid ID") }
        }
    }

    private fun fetchAnimalDetail(animalID: Int) {
        viewModelScope.launch {
            try {
                val result = getDataUseCase.getAnimalDetail(animalID)
                _animal.update { UiState.Success(result) }
            } catch (e: Exception) {
                _animal.update { UiState.Error(e.message ?: "Unknown error") }
            }
        }
    }
}