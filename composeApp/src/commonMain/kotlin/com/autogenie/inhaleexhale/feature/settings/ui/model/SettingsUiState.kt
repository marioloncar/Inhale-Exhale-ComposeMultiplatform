package com.autogenie.inhaleexhale.feature.settings.ui.model

data class SettingsUiState(
    val availableThemes: Map<String, List<String>> = emptyMap(),
    val selectedThemeId: String? = null,
    val cycleCount: Int = 1,
    val isInfiniteCycle: Boolean = false
)
