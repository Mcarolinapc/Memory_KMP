package org.example.project.audio



import javazoom.jl.player.Player


actual object AudioPlayer {
    private var player: Player? = null
    private var playThread: Thread? = null
    private var playing = false

    actual fun play() {
        if (playing) return  // No hacer nada si ya está sonando

        val inputStream = AudioPlayer::class.java.getResourceAsStream("/sirena.mp3")
        player = Player(inputStream)
        playing = true

        playThread = Thread {
            try {
                player?.play()
            } finally {
                playing = false
            }
        }
        playThread?.start()
    }

    actual fun pause() {
        // No implementado
    }

    actual fun resume() {
        // No implementado
    }

    actual fun stop() {
        if (playing) {
            player?.close()  // Para la reproducción
            playing = false
            playThread?.interrupt()
            playThread = null
        }
    }

    actual fun isPlaying(): Boolean = playing
}