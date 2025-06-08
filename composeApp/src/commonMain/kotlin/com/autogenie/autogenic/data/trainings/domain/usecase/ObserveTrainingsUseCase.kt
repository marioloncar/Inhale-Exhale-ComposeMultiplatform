package com.autogenie.autogenic.data.trainings.domain.usecase

import com.autogenie.autogenic.data.preferences.domain.PreferencesRepository
import com.autogenie.autogenic.data.trainings.domain.TrainingsRepository
import com.autogenie.autogenic.data.trainings.domain.model.Training
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class ObserveTrainingsUseCase(
    private val trainingsRepository: TrainingsRepository,
    private val preferencesRepository: PreferencesRepository,
) {

    operator fun invoke(): Flow<List<TrainingWithColor>> {
        return combine(
            trainingsRepository.trainings(),
            preferencesRepository.observeSelectedTheme()
        ) { trainings: List<Training>, selectedTheme: Pair<String, List<String>> ->
            val colors = selectedTheme.second

            trainings.mapIndexed { index, training ->
                TrainingWithColor(training, colors[index])
            }
        }
    }

    data class TrainingWithColor(
        val training: Training,
        val color: String,
    )
}
