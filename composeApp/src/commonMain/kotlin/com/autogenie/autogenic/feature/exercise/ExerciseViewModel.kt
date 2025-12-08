package com.autogenie.autogenic.feature.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autogenie.autogenic.data.preferences.data.model.UserData
import com.autogenie.autogenic.data.preferences.domain.PreferencesRepository
import com.autogenie.autogenic.data.trainings.domain.TrainingsRepository
import com.autogenie.autogenic.data.trainings.domain.model.Training
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ExerciseViewModel(
    private val trainingsRepository: TrainingsRepository,
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {

    private val _training = MutableStateFlow<TrainingUiModel?>(null)
    val training: StateFlow<TrainingUiModel?> = _training

    fun loadExercise(id: String) {
        viewModelScope.launch {
            combine(
                trainingsRepository.trainings(),
                preferencesRepository.observeUserData()
            ) { trainings: List<Training>, userData: UserData ->

                val colors = userData.selectedTheme.second

                trainings.mapIndexed { index, training ->
                    TrainingUiModel(
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

    data class TrainingUiModel(
        val training: Training,
        val color: String,
        val isInfiniteCycle: Boolean
    )
}
