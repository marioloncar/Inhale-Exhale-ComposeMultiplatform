package com.autogenie.autogenic.data.preferences.data.source.local

import com.autogenie.autogenic.data.preferences.data.model.UserData
import kotlinx.coroutines.flow.Flow

interface PreferencesLocalSource {

    fun observeUserData(): Flow<UserData>

    fun observeAvailableThemes(): Flow<Map<String, List<String>>>

    suspend fun setTheme(id: String)

    suspend fun setCycleCount(count: Int)

    suspend fun setInfiniteCycle(infinite: Boolean)
}
