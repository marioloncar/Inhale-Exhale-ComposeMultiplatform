package com.autogenie.autogenic.data.preferences.data.source.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

class PreferencesLocalSourceImpl : PreferencesLocalSource {

    val colorSets = mapOf(
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

    val selectedTheme: MutableStateFlow<Pair<String, List<String>>> = MutableStateFlow(
        colorSets.entries.first().toPair()
    )

    override fun observeAvailableThemes(): Flow<Map<String, List<String>>> {
        return flowOf(colorSets)
    }

    override suspend fun setTheme(id: String) {
        val selectedColors = colorSets[id].orEmpty()
        selectedTheme.emit(id to selectedColors)
    }

    override fun getSelectedTheme(): Flow<Pair<String, List<String>>> {
        return selectedTheme
    }
}
