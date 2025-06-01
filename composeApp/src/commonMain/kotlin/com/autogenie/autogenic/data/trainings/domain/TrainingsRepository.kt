package com.autogenie.autogenic.data.trainings.domain

import com.autogenie.autogenic.data.trainings.domain.model.Training
import kotlinx.coroutines.flow.Flow

interface TrainingsRepository {

    fun trainings(): Flow<List<Training>>
}
