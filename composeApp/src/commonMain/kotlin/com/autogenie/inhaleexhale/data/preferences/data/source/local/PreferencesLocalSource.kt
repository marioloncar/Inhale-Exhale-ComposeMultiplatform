package com.autogenie.inhaleexhale.data.preferences.data.source.local

import com.autogenie.inhaleexhale.data.preferences.data.model.UserData
import kotlinx.coroutines.flow.Flow

interface PreferencesLocalSource {

    fun observeUserData(): Flow<UserData>

    fun observeAvailableThemes(): Flow<Map<String, List<String>>>

    suspend fun setTheme(id: String)

    suspend fun setInfiniteCycle(infinite: Boolean)
}
