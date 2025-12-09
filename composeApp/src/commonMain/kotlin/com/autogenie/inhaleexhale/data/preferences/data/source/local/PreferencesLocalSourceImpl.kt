package com.autogenie.inhaleexhale.data.preferences.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.autogenie.inhaleexhale.data.preferences.data.model.UserData
import com.autogenie.inhaleexhale.data.preferences.data.source.local.model.colorSets
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class PreferencesLocalSourceImpl(
    private val dataStore: DataStore<Preferences>
) : PreferencesLocalSource {

    private companion object {
        val KEY_THEME = stringPreferencesKey("selected_theme_name")
        val KEY_INFINITE = booleanPreferencesKey("use_infinite_cycles")
    }

    override fun observeUserData(): Flow<UserData> {
        return dataStore.data.map { prefs ->

            val themeName = prefs[KEY_THEME] ?: colorSets.keys.first()
            val infiniteCycles = prefs[KEY_INFINITE] ?: false

            val colors = colorSets[themeName].orEmpty()

            UserData(
                selectedTheme = themeName to colors,
                useInfiniteCycles = infiniteCycles
            )
        }
    }

    override fun observeAvailableThemes(): Flow<Map<String, List<String>>> {
        return flowOf(colorSets)
    }

    override suspend fun setTheme(id: String) {
        dataStore.edit { prefs ->
            prefs[KEY_THEME] = id
        }
    }

    override suspend fun setInfiniteCycle(infinite: Boolean) {
        dataStore.edit { prefs ->
            prefs[KEY_INFINITE] = infinite
        }
    }
}
