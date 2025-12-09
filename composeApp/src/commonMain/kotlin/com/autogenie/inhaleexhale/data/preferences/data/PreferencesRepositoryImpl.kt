package com.autogenie.inhaleexhale.data.preferences.data

import com.autogenie.inhaleexhale.data.preferences.data.model.UserData
import com.autogenie.inhaleexhale.data.preferences.data.source.local.PreferencesLocalSource
import com.autogenie.inhaleexhale.data.preferences.domain.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class PreferencesRepositoryImpl(
    private val preferencesLocalSource: PreferencesLocalSource
) : PreferencesRepository {

    override fun observeUserData(): Flow<UserData> {
        return preferencesLocalSource.observeUserData()
    }

    override fun observeAvailableThemes(): Flow<Map<String, List<String>>> {
        return preferencesLocalSource.observeAvailableThemes()
    }

    override suspend fun setTheme(id: String) {
        preferencesLocalSource.setTheme(id)
    }

    override suspend fun setInfiniteCycle(infinite: Boolean) {
        preferencesLocalSource.setInfiniteCycle(infinite)
    }
}
