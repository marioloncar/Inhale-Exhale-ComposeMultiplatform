package com.autogenie.autogenic.core.util

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.ToneGenerator
import android.net.Uri

actual object SoundPlayer {

    private var mediaPlayer: MediaPlayer? = null

    fun initialize(context: Context) {
        mediaPlayer = MediaPlayer.create(context, Uri.parse("raw/tap_sound.mp3"))
    }

    actual fun playGenericSound() {
        mediaPlayer?.start()
    }

    actual fun playPauseSound() {
        val toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
        toneGen.startTone(ToneGenerator.TONE_PROP_BEEP2, 300)
    }
}
