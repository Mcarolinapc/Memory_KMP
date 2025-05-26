package org.example.project.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import memorymichapp.composeapp.generated.resources.Res
import memorymichapp.composeapp.generated.resources.*
import org.example.project.model.CardMemory
import org.jetbrains.compose.resources.DrawableResource

class MemoryViewModel : ViewModel() {

    var dificultad by mutableStateOf("facil")
        private set

    var tipo by mutableStateOf("flor")
        private set

    var cards by mutableStateOf<List<CardMemory>>(emptyList())
        private set

    // Para controlar el estado interno del juego
    private var indexCartaVolteada: Int? = null
    private var movimientos = 0

    // Funciones para actualizar estado (puedes llamarlas desde la UI)

    fun actualizarDificultad(nueva: String) {
        dificultad = nueva
    }

    fun actualizarTipo(nuevo: String) {
        tipo = nuevo
    }

    // Inicia el juego con dificultad y tipo, genera cartas
    fun startGame(dificultad: String, tipo: String) {
        this.dificultad = dificultad
        this.tipo = tipo
        cards = generarCartas(dificultad, tipo)
        indexCartaVolteada = null
        movimientos = 0
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


    // Función que se llama al tocar una carta
    fun voltearCarta(indice: Int, onGameFinished: (movimientos: Int) -> Unit) {
        // Si la carta ya está emparejada o volteada, no hacer nada
        val carta = cards[indice]
        if (carta.isFaceUp || carta.isMatched) return

        val newCards = cards.toMutableList()

        // Voltear carta seleccionada
        newCards[indice] = carta.copy(isFaceUp = true)
        cards = newCards

        if (indexCartaVolteada == null) {
            // Primera carta volteada
            indexCartaVolteada = indice
        } else {
            // Segunda carta volteada, comparar
            movimientos++
            val primerIndice = indexCartaVolteada!!
            val segundaIndice = indice

            val primeraCarta = cards[primerIndice]

            if (primeraCarta.imageResId == carta.imageResId) {
                // Coinciden -> marcar como emparejadas
                newCards[primerIndice] = primeraCarta.copy(isMatched = true)
                newCards[segundaIndice] = carta.copy(isMatched = true, isFaceUp = true)
                cards = newCards
            } else {
                // No coinciden -> dar tiempo para que el usuario vea, luego ocultar
                // Aquí necesitas un delay (se puede hacer con Coroutine en Compose)
                // Para ejemplo, usamos LaunchedEffect en Compose, no aquí en ViewModel
            }
            indexCartaVolteada = null

            // Comprobar si juego terminado
            if (newCards.all { it.isMatched }) {
                onGameFinished(movimientos)
            }
        }
    }

    // Para ocultar cartas no coincidentes después de un delay (llámalo desde Compose)
    fun ocultarCartasNoEmparejadas(indice1: Int, indice2: Int) {
        val newCards = cards.toMutableList()
        newCards[indice1] = newCards[indice1].copy(isFaceUp = false)
        newCards[indice2] = newCards[indice2].copy(isFaceUp = false)
        cards = newCards
    }
}
