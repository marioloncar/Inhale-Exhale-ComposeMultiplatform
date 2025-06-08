package com.autogenie.autogenic.feature.settings.ui.model

data class SettingsUiState(
    val availableThemes: Map<String, List<String>> = emptyMap(),
    val selectedThemeId: String? = null,
)
