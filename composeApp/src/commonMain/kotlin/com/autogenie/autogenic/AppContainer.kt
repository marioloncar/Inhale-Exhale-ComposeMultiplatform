package com.autogenie.autogenic

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.autogenie.autogenic.data.preferences.data.PreferencesRepositoryImpl
import com.autogenie.autogenic.data.preferences.data.source.local.PreferencesLocalSource
import com.autogenie.autogenic.data.preferences.data.source.local.PreferencesLocalSourceImpl
import com.autogenie.autogenic.data.preferences.domain.PreferencesRepository
import com.autogenie.autogenic.data.trainings.data.TrainingsRepositoryImpl
import com.autogenie.autogenic.data.trainings.data.source.remote.TrainingsRemoteSource
import com.autogenie.autogenic.data.trainings.data.source.remote.TrainingsRemoteSourceImpl
import com.autogenie.autogenic.data.trainings.domain.TrainingsRepository

object AppContainer {

    lateinit var userPreferences: DataStore<Preferences>

    fun initialize(dataStore: DataStore<Preferences>) {
        userPreferences = dataStore
    }

    private val trainingsRemoteSource: TrainingsRemoteSource by lazy {
        TrainingsRemoteSourceImpl()
    }

    val trainingsRepository: TrainingsRepository by lazy {
        TrainingsRepositoryImpl(trainingsRemoteSource)
    }

    val preferencesRepository: PreferencesRepository by lazy {
        PreferencesRepositoryImpl(preferencesLocalSource)
    }

    private val preferencesLocalSource: PreferencesLocalSource by lazy {
        PreferencesLocalSourceImpl(userPreferences)
    }
}
