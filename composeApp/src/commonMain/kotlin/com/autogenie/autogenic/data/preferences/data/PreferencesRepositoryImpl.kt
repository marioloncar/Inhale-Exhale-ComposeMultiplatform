package com.autogenie.autogenic.data.preferences.data

import com.autogenie.autogenic.data.preferences.data.source.local.PreferencesLocalSource
import com.autogenie.autogenic.data.preferences.domain.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class PreferencesRepositoryImpl(
    private val preferencesLocalSource: PreferencesLocalSource
) : PreferencesRepository {

    override fun observeAvailableThemes(): Flow<Map<String, List<String>>> {
        return preferencesLocalSource.observeAvailableThemes()
    }

    override suspend fun setTheme(id: String) {
        preferencesLocalSource.setTheme(id)
    }

    override fun observeSelectedTheme(): Flow<Pair<String, List<String>>> {
        return preferencesLocalSource.getSelectedTheme()
    }
}
