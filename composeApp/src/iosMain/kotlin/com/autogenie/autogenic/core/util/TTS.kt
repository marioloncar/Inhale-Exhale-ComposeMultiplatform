package com.autogenie.autogenic.core.util

import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechUtterance
import platform.AVFAudio.AVSpeechUtteranceDefaultSpeechRate

actual object TTS {
    private val synthesizer = AVSpeechSynthesizer()

    actual fun initialize(platformContext: Any?) {
    }

    actual fun speak(text: String) {
        val utterance = AVSpeechUtterance(string = text)
        utterance.rate = AVSpeechUtteranceDefaultSpeechRate
        synthesizer.speakUtterance(utterance)
    }

    actual fun stop() {

    }
}
