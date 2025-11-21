package com.autogenie.autogenic.core.util

expect object TTS {
    fun initialize(platformContext: Any? = null)
    fun speak(text: String)
    fun stop()
}