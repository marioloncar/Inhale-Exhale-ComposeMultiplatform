package com.autogenie.autogenic.feature.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autogenie.autogenic.data.trainings.domain.usecase.ObserveTrainingsUseCase
import com.autogenie.autogenic.data.trainings.domain.usecase.ObserveTrainingsUseCase.TrainingWithColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExerciseViewModel(
    private val observeTrainingsUseCase: ObserveTrainingsUseCase,
) : ViewModel() {

    private val _training = MutableStateFlow<TrainingWithColor?>(null)
    val training: StateFlow<TrainingWithColor?> = _training

    fun loadExercise(id: String) {
        viewModelScope.launch {
            observeTrainingsUseCase()
                .collect { trainings ->
                    _training.value = trainings.find { it.training.id == id }
                }
        }
    }
}
