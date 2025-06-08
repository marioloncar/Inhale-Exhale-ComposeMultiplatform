package com.autogenie.autogenic

import com.autogenie.autogenic.data.preferences.data.PreferencesRepositoryImpl
import com.autogenie.autogenic.data.preferences.data.source.local.PreferencesLocalSource
import com.autogenie.autogenic.data.preferences.data.source.local.PreferencesLocalSourceImpl
import com.autogenie.autogenic.data.preferences.domain.PreferencesRepository
import com.autogenie.autogenic.data.trainings.data.TrainingsRepositoryImpl
import com.autogenie.autogenic.data.trainings.data.source.remote.TrainingsRemoteSource
import com.autogenie.autogenic.data.trainings.data.source.remote.TrainingsRemoteSourceImpl
import com.autogenie.autogenic.data.trainings.domain.TrainingsRepository
import com.autogenie.autogenic.data.trainings.domain.usecase.ObserveTrainingsUseCase

object AppContainer {

    private val trainingsRemoteSource: TrainingsRemoteSource by lazy {
        TrainingsRemoteSourceImpl()
    }

    val trainingsRepository: TrainingsRepository by lazy {
        TrainingsRepositoryImpl(trainingsRemoteSource)
    }

    val preferencesRepository: PreferencesRepository by lazy {
        PreferencesRepositoryImpl(preferencesLocalSource)
    }

    val observeTrainingsUseCase: ObserveTrainingsUseCase by lazy {
        ObserveTrainingsUseCase(
            trainingsRepository = trainingsRepository,
            preferencesRepository = preferencesRepository
        )
    }

    private val preferencesLocalSource: PreferencesLocalSource by lazy {
        PreferencesLocalSourceImpl()
    }


}
