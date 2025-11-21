package com.autogenie.autogenic.core.util

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
            tts?.language = Locale.US
            isInitialized = true
        }
    }

    actual fun speak(text: String) {
        if (!isInitialized) return

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
    }
}
