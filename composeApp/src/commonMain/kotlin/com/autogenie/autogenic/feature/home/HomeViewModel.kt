package com.autogenie.autogenic.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autogenie.autogenic.data.trainings.domain.TrainingsRepository
import com.autogenie.autogenic.feature.home.ui.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val trainingsRepository: TrainingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            trainingsRepository.trainings()
                .collect {
                    _uiState.emit(_uiState.value.copy(trainings = it))
                }
        }
    }
}
