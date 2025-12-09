package com.autogenie.inhaleexhale.data.preferences.domain

import com.autogenie.inhaleexhale.data.preferences.data.model.UserData
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    fun observeUserData(): Flow<UserData>

    fun observeAvailableThemes(): Flow<Map<String, List<String>>>

    suspend fun setTheme(id: String)

    suspend fun setInfiniteCycle(infinite: Boolean)
}
