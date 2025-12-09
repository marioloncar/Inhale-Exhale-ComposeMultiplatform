package com.autogenie.inhaleexhale.feature.exercise.ui.model

import com.autogenie.inhaleexhale.data.trainings.domain.model.Training

data class ExerciseUiState(
    val training: Training,
    val color: String,
    val isInfiniteCycle: Boolean
)