package com.autogenie.inhaleexhale.data.trainings.data

import com.autogenie.inhaleexhale.data.trainings.data.source.remote.TrainingsRemoteSource
import com.autogenie.inhaleexhale.data.trainings.data.source.remote.model.StepDto
import com.autogenie.inhaleexhale.data.trainings.data.source.remote.model.TrainingDto
import com.autogenie.inhaleexhale.data.trainings.domain.TrainingsRepository
import com.autogenie.inhaleexhale.data.trainings.domain.model.StepType
import com.autogenie.inhaleexhale.data.trainings.domain.model.Training
import com.autogenie.inhaleexhale.data.trainings.domain.model.TrainingStep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TrainingsRepositoryImpl(
    val trainingsRemoteSource: TrainingsRemoteSource
) : TrainingsRepository {

    override fun trainings(): Flow<List<Training>> = flow {
        emit(trainingsRemoteSource.fetchTrainings().map(TrainingDto::toDomain))
    }
}

private fun TrainingDto.toDomain(): Training = Training(
    id = id,
    name = name,
    summary = summary,
    description = description,
    cycles = cycles,
    steps = steps.map { it.toDomain() }
)

private fun StepDto.toDomain(): TrainingStep = TrainingStep(
    type = StepType.valueOf(type.uppercase()),
    duration = duration
)
