package com.autogenie.autogenic.data.preferences.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.autogenie.autogenic.data.preferences.data.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

private val colorSets = mapOf(
    "Ocean Breeze" to listOf(
        "#FF2196F3",
        "#FF03A9F4",
        "#FF00BCD4",
        "#FFB3E5FC",
        "#FF81D4FA"
    ),
    "Sunset Glow" to listOf(
        "#FFFF9800",
        "#FFFF5722",
        "#FFFF7043",
        "#FFFFB74D",
        "#FFFFCCBC"
    ),
    "Forest Calm" to listOf(
        "#FF4CAF50",
        "#FF2E7D32",
        "#FF81C784",
        "#FF66BB6A",
        "#FFC8E6C9"
    ),
    "Midnight" to listOf(
        "#FF212121",
        "#FF424242",
        "#FF37474F",
        "#FF607D8B",
        "#FF90A4AE"
    ),
    "Candy Pop" to listOf(
        "#FFE91E63",
        "#FFFF4081",
        "#FFFFC107",
        "#FFFFEB3B",
        "#FFCDDC39"
    )
)

class PreferencesLocalSourceImpl(
    private val dataStore: DataStore<Preferences>
) : PreferencesLocalSource {

    private companion object {
        val KEY_THEME = stringPreferencesKey("selected_theme_name")
        val KEY_CYCLE_COUNT = intPreferencesKey("selected_cycle_count")
        val KEY_INFINITE = booleanPreferencesKey("use_infinite_cycles")
    }

    override fun observeUserData(): Flow<UserData> {
        return dataStore.data.map { prefs ->

            val themeName = prefs[KEY_THEME] ?: colorSets.keys.first()
            val cycleCount = prefs[KEY_CYCLE_COUNT] ?: 1
            val infiniteCycles = prefs[KEY_INFINITE] ?: false

            val colors = colorSets[themeName].orEmpty()

            UserData(
                selectedTheme = themeName to colors,
                selectedCycleCount = cycleCount,
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

    override suspend fun setCycleCount(count: Int) {
        dataStore.edit { prefs ->
            prefs[KEY_CYCLE_COUNT] = count
        }
    }

    override suspend fun setInfiniteCycle(infinite: Boolean) {
        dataStore.edit { prefs ->
            prefs[KEY_INFINITE] = infinite
        }
    }
}
