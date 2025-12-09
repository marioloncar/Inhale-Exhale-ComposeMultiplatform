package com.autogenie.inhaleexhale.data.preferences.data.model

data class UserData(
    val selectedTheme: Pair<String, List<String>>,
    val useInfiniteCycles: Boolean,
)
