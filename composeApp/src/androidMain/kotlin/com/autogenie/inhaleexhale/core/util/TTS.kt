package com.autogenie.inhaleexhale.core.util

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

actual object TTS : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isInitialized = false

    actual fun initialize(platformContext: Any?) {
        if (platformContext !is Context) return
        if (tts == null) {
            tts = TextToSpeech(platformContext.applicationContext, this)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // 1. Set the language
            tts?.language = Locale.US

            // 2. Adjust Pitch and Rate to sound more "human"
            // Pitch: 1.0 is default. 0.9 - 1.1 feels more natural.
            // Rate: 1.0 is default. 0.85 - 0.95 feels less rushed and robotic.
            tts?.setPitch(1.05f)
            tts?.setSpeechRate(0.9f)

            // 3. Try to find a high-quality (non-network) voice
            try {
                val bestVoice = tts?.voices?.find { voice ->
                    voice.name.contains("en-us-x", ignoreCase = true) &&
                            !voice.isNetworkConnectionRequired
                }
                bestVoice?.let { tts?.voice = it }
            } catch (e: Exception) {
                // Fallback to default if voices list is unavailable
            }

            isInitialized = true
        }
    }

    actual fun speak(text: String) {
        if (!isInitialized) return

        // Adding a slight delay or specific params can help clarity
        tts?.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "tts-${System.currentTimeMillis()}"
        )
    }

    actual fun stop() {
        tts?.stop()
        tts?.shutdown()
        isInitialized = false
        tts = null
    }
}
