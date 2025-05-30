package com.autogenie.autogenic

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.autogenie.autogenic.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "autogenie",
    ) {
        App()
    }
}