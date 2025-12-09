package com.autogenie.inhaleexhale.feature.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autogenie.inhaleexhale.data.preferences.data.model.UserData
import com.autogenie.inhaleexhale.data.preferences.domain.PreferencesRepository
import com.autogenie.inhaleexhale.data.trainings.domain.TrainingsRepository
import com.autogenie.inhaleexhale.data.trainings.domain.model.Training
import com.autogenie.inhaleexhale.feature.exercise.ui.model.ExerciseUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ExerciseViewModel(
    private val trainingsRepository: TrainingsRepository,
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {

    private val _training = MutableStateFlow<ExerciseUiState?>(null)
    val training: StateFlow<ExerciseUiState?> = _training

    fun loadExercise(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                trainingsRepository.trainings(),
                preferencesRepository.observeUserData()
            ) { trainings: List<Training>, userData: UserData ->

                val colors = userData.selectedTheme.second

                trainings.mapIndexed { index, training ->
                    ExerciseUiState(
                        training = training,
                        color = colors[index % colors.size],
                        isInfiniteCycle = userData.useInfiniteCycles
                    )
                }
            }
                .collect { list ->
                    _training.value = list.find { it.training.id == id }
                }
        }
    }
}
