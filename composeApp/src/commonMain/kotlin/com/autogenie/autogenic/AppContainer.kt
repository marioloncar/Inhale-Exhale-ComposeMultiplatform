package com.autogenie.autogenic

import com.autogenie.autogenic.data.trainings.data.TrainingsRepositoryImpl
import com.autogenie.autogenic.data.trainings.data.source.remote.TrainingsRemoteSource
import com.autogenie.autogenic.data.trainings.data.source.remote.TrainingsRemoteSourceImpl
import com.autogenie.autogenic.data.trainings.domain.TrainingsRepository

object AppContainer {

    private val trainingsRemoteSource: TrainingsRemoteSource by lazy {
        TrainingsRemoteSourceImpl()
    }

    val trainingsRepository: TrainingsRepository by lazy {
        TrainingsRepositoryImpl(trainingsRemoteSource)
    }
}
