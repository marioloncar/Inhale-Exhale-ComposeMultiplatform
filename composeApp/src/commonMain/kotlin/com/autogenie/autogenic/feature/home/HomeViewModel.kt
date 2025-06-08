package com.autogenie.autogenic.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autogenie.autogenic.data.trainings.domain.usecase.ObserveTrainingsUseCase
import com.autogenie.autogenic.feature.home.ui.model.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val observeTrainingsUseCase: ObserveTrainingsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeTrainingsUseCase()
                .collect {
                    _uiState.emit(_uiState.value.copy(trainings = it))
                }
        }
    }
}
