package com.autogenie.inhaleexhale.core.util

expect object TTS {
    fun initialize(platformContext: Any? = null)
    fun speak(text: String)
    fun stop()
}