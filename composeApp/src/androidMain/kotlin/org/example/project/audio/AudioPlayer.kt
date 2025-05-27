package org.example.project.audio

import android.media.MediaPlayer
import org.example.project.AppContextHolder
import org.example.project.R

actual object AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null
    private var wasPaused = false

    actual fun play() {
        if (mediaPlayer == null) {
            val context = AppContextHolder.context
            mediaPlayer = MediaPlayer.create(context, R.raw.sirena).apply {
                isLooping = false // o true si es música de fondo
                setVolume(1.0f, 1.0f)
                start()
            }
        } else if (!mediaPlayer!!.isPlaying && wasPaused) {
            mediaPlayer?.start()
            wasPaused = false
        }
    }

    actual fun pause() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
            wasPaused = true
        }
    }

    actual fun resume() {
        if (wasPaused && mediaPlayer != null) {
            mediaPlayer?.start()
            wasPaused = false
        }
    }

    actual fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        wasPaused = false
    }

    actual fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying == true
    }
}
