package com.autogenie.autogenic.data.preferences.domain

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    fun observeAvailableThemes(): Flow<Map<String, List<String>>>

    suspend fun setTheme(id: String)

    fun observeSelectedTheme(): Flow<Pair<String, List<String>>>
}
