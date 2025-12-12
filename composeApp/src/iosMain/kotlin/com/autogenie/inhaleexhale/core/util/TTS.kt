package com.autogenie.inhaleexhale.core.util

import platform.AVFAudio.AVSpeechSynthesisVoice
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
        utterance.voice = AVSpeechSynthesisVoice.voiceWithLanguage("en-US")
        synthesizer.speakUtterance(utterance)
    }

    actual fun stop() {

    }
}
