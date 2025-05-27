package org.example.project.audio

expect object AudioPlayer {
    fun play()
    fun pause()
    fun resume()
    fun stop()
    fun isPlaying(): Boolean
}
