package com.autogenie.inhaleexhale.data.trainings.data.source.remote

import com.autogenie.inhaleexhale.data.trainings.data.source.remote.model.TrainingDto

interface TrainingsRemoteSource {

    suspend fun fetchTrainings(): List<TrainingDto>

    suspend fun fetchTraining(id: String): TrainingDto?
}
