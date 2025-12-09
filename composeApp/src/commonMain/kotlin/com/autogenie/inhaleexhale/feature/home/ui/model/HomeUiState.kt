package com.autogenie.inhaleexhale.feature.home.ui.model

import com.autogenie.inhaleexhale.feature.home.TrainingWithColor

data class HomeUiState(
    val trainings: List<TrainingWithColor> = emptyList()
)