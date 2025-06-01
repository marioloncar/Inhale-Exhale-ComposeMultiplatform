package com.autogenie.autogenic.data.trainings.data.source.remote

import com.autogenie.autogenic.data.trainings.data.source.remote.model.TrainingDto

interface TrainingsRemoteSource {

    suspend fun fetchTrainings(): List<TrainingDto>
}
