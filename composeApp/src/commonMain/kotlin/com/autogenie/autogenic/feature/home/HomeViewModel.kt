package com.autogenie.autogenic.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autogenie.autogenic.data.preferences.domain.PreferencesRepository
import com.autogenie.autogenic.data.trainings.domain.TrainingsRepository
import com.autogenie.autogenic.data.trainings.domain.model.Training
import com.autogenie.autogenic.feature.home.ui.model.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val trainingsRepository: TrainingsRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch {
            combine(
                trainingsRepository.trainings(),
                preferencesRepository.observeUserData().map { it.selectedTheme.second }
            ) { trainings, colors ->
                trainings.mapIndexed { index, training ->
                    TrainingWithColor(
                        training = training,
                        color = colors[index % colors.size]
                    )
                }
            }.collect { trainingsWithColor ->
                _uiState.value = _uiState.value.copy(trainings = trainingsWithColor)
            }
        }
    }
}

data class TrainingWithColor(
    val training: Training,
    val color: String
)