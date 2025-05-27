package org.example.project.audio

import kotlinx.browser.document
import org.w3c.dom.HTMLAudioElement

actual object AudioPlayer {
    private var audio: HTMLAudioElement? = null

    actual fun play() {
        if (audio == null) {
            audio = (document.createElement("audio") as HTMLAudioElement).apply {
                src = "sirena.mp3"
                autoplay = true
                loop = true
                volume = 1.0
            }
        } else {
            audio?.play()
        }
    }

    actual fun pause() {
        audio?.pause()
    }

    actual fun resume() {
        audio?.play()
    }

    actual fun stop() {
        audio?.pause()
        audio?.currentTime = 0.0
        audio = null
    }

    actual fun isPlaying(): Boolean {
        return audio?.paused?.not() ?: false
    }
}