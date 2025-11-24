package com.autogenie.autogenic.data.preferences.data.model

data class UserData(
    val selectedTheme: Pair<String, List<String>>,
    val selectedCycleCount: Int,
    val useInfiniteCycles: Boolean,
)
