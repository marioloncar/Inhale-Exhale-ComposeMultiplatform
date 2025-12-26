package com.autogenie.inhaleexhale.core.util

import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechUtterance

actual object TTS {
    private val synthesizer = AVSpeechSynthesizer()

    actual fun initialize(platformContext: Any?) {
        // No context needed for iOS
    }

    actual fun speak(text: String) {
        val utterance = AVSpeechUtterance(string = text)

        // 1. Find the highest quality voice (Siri/Premium)
        // We look for "Ava" or "Siri" which are usually the most natural
        val premiumVoice = AVSpeechSynthesisVoice.speechVoices()
            .map { it as AVSpeechSynthesisVoice }
            .find { it.language == "en-US" && it.quality == platform.AVFAudio.AVSpeechSynthesisVoiceQualityEnhanced }
            ?: AVSpeechSynthesisVoice.voiceWithLanguage("en-US")

        utterance.voice = premiumVoice

        // 2. Adjust Rate, Pitch, and Volume
        // 0.45f - 0.5f is usually sweet spot for breathing exercises
        utterance.rate = 0.48f
        utterance.pitchMultiplier = 1.05f
        utterance.volume = 1.0f

        // 3. Add natural pauses
        // This stops the speech from feeling like one giant run-on sentence
        utterance.postUtteranceDelay = 0.1

        synthesizer.speakUtterance(utterance)
    }

    actual fun stop() {
        if (synthesizer.isSpeaking()) {
            synthesizer.stopSpeakingAtBoundary(platform.AVFAudio.AVSpeechBoundary.AVSpeechBoundaryImmediate)
        }
    }
}