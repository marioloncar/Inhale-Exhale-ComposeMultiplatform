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
        "#FF2196F3", // deep blue
        "#FF00BCD4", // teal
        "#FFFFC107", // amber
        "#FF4CAF50", // green
        "#FFFF5722"  // orange
    ),
    "Sunset Glow" to listOf(
        "#FFFF7043", // coral
        "#FFFFEB3B", // yellow
        "#FF8E24AA", // purple
        "#FF03A9F4", // bright blue
        "#FFFF4081"  // pink
    ),
    "Forest Calm" to listOf(
        "#FF4CAF50", // green
        "#FFCDDC39", // lime
        "#FF8D6E63", // brown
        "#FF00BCD4", // teal
        "#FF81C784"  // soft green
    ),
    "Candy Pop" to listOf(
        "#FFE91E63", // pink
        "#FFFFEB3B", // yellow
        "#FF03A9F4", // bright blue
        "#FFCDDC39", // lime
        "#FFFF5722"  // orange
    ),
    "Morning Mist" to listOf(
        "#FF7E57C2", // lavender
        "#FF64B5F6", // light blue
        "#FFFFF176", // pale yellow
        "#FF4DB6AC", // teal
        "#FFFF8A65"  // soft coral
    )
)


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
