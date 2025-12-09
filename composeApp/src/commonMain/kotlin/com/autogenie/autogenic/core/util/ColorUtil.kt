package com.autogenie.autogenic.core.util

import androidx.compose.ui.graphics.Color

fun String.toColor(): Color {
    val hex = this.removePrefix("#")
    val colorLong = when (hex.length) {
        6 -> "FF$hex".toLong(16)
        8 -> hex.toLong(16)
        else -> throw IllegalArgumentException("Invalid color format: $this")
    }
    return Color(colorLong)
}
