package com.autogenie.inhaleexhale.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autogenie.inhaleexhale.data.preferences.domain.PreferencesRepository
import com.autogenie.inhaleexhale.data.trainings.domain.TrainingsRepository
import com.autogenie.inhaleexhale.data.trainings.domain.model.Training
import com.autogenie.inhaleexhale.feature.home.ui.model.HomeUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
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
        viewModelScope.launch(Dispatchers.IO) {
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