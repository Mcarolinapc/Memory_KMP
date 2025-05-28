package org.example.project.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import memorymichapp.composeapp.generated.resources.Res
import memorymichapp.composeapp.generated.resources.*
import org.example.project.audio.AudioPlayer
import org.example.project.model.CardMemory
import org.jetbrains.compose.resources.DrawableResource


class MemoryViewModel : ViewModel() {

    var dificultad by mutableStateOf("facil")
        private set

    var tipo by mutableStateOf("flor")
        private set

    private var indexCartaVolteada: Int? = null
    private var movimientos = 0

    var cards by mutableStateOf<List<CardMemory>>(emptyList())
        private set

    var cartasParaOcultar by mutableStateOf<Pair<Int, Int>?>(null)

    var musicaActiva by mutableStateOf(true)

    init {
        if (musicaActiva) {
            AudioPlayer.play()
        } else {
            AudioPlayer.pause()
        }
    }

    //futuras actulizaciones

   fun actualizarDificultad(nueva: String) {
        dificultad = nueva
   }
    //actualizaciones
    fun actualizarTipo(nuevo: String) {
        tipo = nuevo
    }

    fun toggleMusica() {
        musicaActiva = !musicaActiva
        if (musicaActiva) {
            AudioPlayer.play()
        } else {
            AudioPlayer.stop()
        }
    }
    fun startGame(dificultad: String, tipo: String) {
        this.dificultad = dificultad
        this.tipo = tipo
        cards = generarCartas(dificultad, tipo)
        indexCartaVolteada = null
        movimientos = 0
        cartasParaOcultar = null

        val duracion = when (dificultad.lowercase()) {
            "facil" -> 60
            "medio" -> 120
            "dificil" -> 180
            else -> 60
        }
        _tiempoRestante.value = duracion
        _paresEncontrados.value = 0
        _juegoFinalizado.value = false
        iniciarTemporizador(duracion)
    }

    private fun generarCartas(dificultad: String, tipo: String): List<CardMemory> {
        val images = getImagesForTipo(tipo).shuffled()

        val cantidad = when (dificultad.lowercase()) {
            "facil" -> 4
            "medio" -> 8
            "dificil" -> 16
            else -> 4
        }

        val seleccionadas = images.take(cantidad)
        val cartas = (seleccionadas + seleccionadas).shuffled()

        return cartas.mapIndexed { index, imageRes ->
            CardMemory(
                uniqueId = index,
                imageResId = imageRes,
                isFaceUp = false,
                isMatched = false
            )
        }
    }

    fun voltearCarta(indice: Int, onGameFinished: (movimientos: Int) -> Unit) {
        val carta = cards[indice]
        if (carta.isFaceUp || carta.isMatched) return

        val newCards = cards.toMutableList()
        newCards[indice] = carta.copy(isFaceUp = true)
        cards = newCards

        if (indexCartaVolteada == null) {
            indexCartaVolteada = indice
        } else {
            movimientos++
            val primerIndice = indexCartaVolteada!!
            val segundaIndice = indice

            val primeraCarta = cards[primerIndice]

            if (primeraCarta.imageResId == carta.imageResId) {
                newCards[primerIndice] = primeraCarta.copy(isMatched = true)
                newCards[segundaIndice] = carta.copy(isMatched = true, isFaceUp = true)
                cards = newCards
                incrementarPares()
            } else {
                cartasParaOcultar = Pair(primerIndice, segundaIndice)
            }
            indexCartaVolteada = null

            if (newCards.all { it.isMatched }) {
                onGameFinished(movimientos)
            }
        }
    }

    fun ocultarCartasNoEmparejadas() {
        cartasParaOcultar?.let { (i1, i2) ->
            val newCards = cards.toMutableList()
            newCards[i1] = newCards[i1].copy(isFaceUp = false)
            newCards[i2] = newCards[i2].copy(isFaceUp = false)
            cards = newCards
        }
        cartasParaOcultar = null
    }

    // esto si da tiempo lo paso a un provider
    private fun getImagesForTipo(tipo: String): List<DrawableResource> = when (tipo.lowercase()) {
        "flor" -> listOf(
            Res.drawable.flor_1, Res.drawable.flor_2, Res.drawable.flor_3,
            Res.drawable.flor_4, Res.drawable.flor_5, Res.drawable.flor_6,
            Res.drawable.flor_7, Res.drawable.flor_8, Res.drawable.flor_9,
            Res.drawable.flor_10, Res.drawable.flor_11, Res.drawable.flor_12,
            Res.drawable.flor_13, Res.drawable.flor_14, Res.drawable.flor_15,
            Res.drawable.flor_16
        )
        "grupo" -> listOf(
            Res.drawable.grupo_1, Res.drawable.grupo_2, Res.drawable.grupo_3,
            Res.drawable.grupo_4, Res.drawable.grupo_5, Res.drawable.grupo_6,
            Res.drawable.grupo_7, Res.drawable.grupo_8, Res.drawable.grupo_9,
            Res.drawable.grupo_10, Res.drawable.grupo_11, Res.drawable.grupo_12,
            Res.drawable.grupo_13, Res.drawable.grupo_14, Res.drawable.grupo_15,
            Res.drawable.grupo_16
        )
        "itb" -> listOf(
            Res.drawable.itb_1, Res.drawable.itb_2, Res.drawable.itb_3,
            Res.drawable.itb_4, Res.drawable.itb_5, Res.drawable.itb_6,
            Res.drawable.itb_7, Res.drawable.itb_8, Res.drawable.itb_9,
            Res.drawable.itb_10, Res.drawable.itb_11, Res.drawable.itb_12,
            Res.drawable.itb_13, Res.drawable.itb_14, Res.drawable.itb_15,
            Res.drawable.itb_16
        )
        else -> emptyList()
    }
    //gestion del resultado

    private val viewModelScope = CoroutineScope(Dispatchers.Main + Job())

    private val _paresEncontrados = MutableStateFlow(0)
    val paresEncontrados: StateFlow<Int> = _paresEncontrados.asStateFlow()

    private val _tiempoRestante = MutableStateFlow(0)
    val tiempoRestante: StateFlow<Int> = _tiempoRestante.asStateFlow()

    private val _juegoFinalizado = MutableStateFlow(false)
    val juegoFinalizado: StateFlow<Boolean> = _juegoFinalizado.asStateFlow()

    private var temporizadorJob: Job? = null

    private fun iniciarTemporizador(segundos: Int) {
        temporizadorJob?.cancel()
        temporizadorJob = viewModelScope.launch {
            var tiempo = segundos
            while (tiempo > 0) {
                delay(1000)
                tiempo--
                _tiempoRestante.value = tiempo
            }
            _juegoFinalizado.value = true
        }
    }

    fun incrementarPares() {
        _paresEncontrados.value = _paresEncontrados.value + 1
    }

    fun reiniciarJuego() {
        temporizadorJob?.cancel()
        _tiempoRestante.value = 0
        _paresEncontrados.value = 0
        _juegoFinalizado.value = false
    }
}
