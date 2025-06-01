package com.autogenie.autogenic.feature.home.ui

import com.autogenie.autogenic.data.trainings.domain.model.Training

data class HomeUiState(
    val trainings: List<Training> = emptyList()
)
