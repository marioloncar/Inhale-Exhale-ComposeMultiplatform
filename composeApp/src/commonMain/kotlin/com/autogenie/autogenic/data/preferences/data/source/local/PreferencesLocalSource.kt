package com.autogenie.autogenic.data.preferences.data.source.local

import kotlinx.coroutines.flow.Flow

interface PreferencesLocalSource {

    fun observeAvailableThemes(): Flow<Map<String, List<String>>>

    suspend fun setTheme(id: String)

    fun getSelectedTheme(): Flow<Pair<String, List<String>>>
}
