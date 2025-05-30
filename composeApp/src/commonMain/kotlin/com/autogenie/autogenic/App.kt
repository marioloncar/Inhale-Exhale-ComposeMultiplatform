package com.autogenie.autogenic

import androidx.compose.runtime.Composable
import com.autogenie.autogenic.core.ui.AppTheme
import com.autogenie.autogenic.feature.home.ui.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AppTheme {
        HomeScreen()
    }
}
