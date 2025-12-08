package com.autogenie.autogenic.feature.home.ui.model

import com.autogenie.autogenic.feature.home.TrainingWithColor

data class HomeUiState(
    val trainings: List<TrainingWithColor> = emptyList()
)