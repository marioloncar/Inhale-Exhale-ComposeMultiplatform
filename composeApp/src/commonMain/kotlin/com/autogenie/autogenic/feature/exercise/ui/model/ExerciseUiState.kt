package com.autogenie.autogenic.feature.exercise.ui.model

import com.autogenie.autogenic.data.trainings.domain.model.Training

data class ExerciseUiState(
    val training: Training,
    val color: String,
    val isInfiniteCycle: Boolean
)