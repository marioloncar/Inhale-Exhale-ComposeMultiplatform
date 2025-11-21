package com.autogenie.autogenic.core.util

actual object TTS {
    actual fun initialize(platformContext: Any?) {}
    actual fun speak(text: String) {}
    actual fun stop() {}
}