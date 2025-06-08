package com.autogenie.autogenic.feature.home.ui.model

import com.autogenie.autogenic.data.trainings.domain.usecase.ObserveTrainingsUseCase.TrainingWithColor

data class HomeUiState(
    val trainings: List<TrainingWithColor> = emptyList()
)