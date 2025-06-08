package com.autogenie.autogenic.data.trainings.data

import com.autogenie.autogenic.data.trainings.data.source.remote.TrainingsRemoteSource
import com.autogenie.autogenic.data.trainings.data.source.remote.model.TrainingDto
import com.autogenie.autogenic.data.trainings.domain.TrainingsRepository
import com.autogenie.autogenic.data.trainings.domain.model.Training
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class TrainingsRepositoryImpl(
    val trainingsRemoteSource: TrainingsRemoteSource
) : TrainingsRepository {

    override fun trainings(): Flow<List<Training>> {
        return flow {
            delay(Random.nextLong(1, 1200)) // simulate network call
            emit(trainingsRemoteSource.fetchTrainings().map(TrainingDto::toDomain))
        }
    }
}

private fun TrainingDto.toDomain(): Training {
    return Training(
        id = id,
        name = name,
        instructions = instructions
    )
}
