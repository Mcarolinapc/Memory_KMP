package org.example.project.screens

import androidx.compose.runtime.Composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CardDefaults
import org.example.project.model.CardMemory
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.viewModel.MemoryViewModel

/*@Composable
fun GameScreen(
    dificultad: String,
    tipo: String,
    onFinish: (Int) -> Unit,
    viewModel: MemoryViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.startGame(dificultad, tipo)
    }

    val cards = viewModel.cards

    var cartasParaOcultar by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    if (cartasParaOcultar != null) {
        LaunchedEffect(cartasParaOcultar) {
            kotlinx.coroutines.delay(1000L)
            viewModel.ocultarCartasNoEmparejadas(
                cartasParaOcultar!!.first,
                cartasParaOcultar!!.second
            )
            cartasParaOcultar = null
        }
    }

    val columnas = columnasPorDificultad(dificultad)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f) // Ocupa el 50% de la altura total
            .padding(16.dp),
            contentAlignment = Alignment.Center
    ) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val spacing = 8.dp * (columnas - 1)
        // Tamaño base calculado para ancho y alto igual (cuadrado)
        val cartaSizeRaw = (maxWidth - spacing - 16.dp) / columnas // 16.dp padding extra

        // Limitamos tamaño para que no sea ni muy pequeño ni muy grande (ajusta valores)
        val cartaSize = cartaSizeRaw.coerceIn(80.dp, 150.dp)

        LazyVerticalGrid(
            columns = GridCells.Fixed(columnas),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(cards) { index, card ->
                Card(
                    modifier = Modifier
                        .size(cartaSize)
                        .clickable {
                            if (cartasParaOcultar == null) {
                                val antes = viewModel.cards
                                viewModel.voltearCarta(index) { movimientos ->
                                    onFinish(movimientos)
                                }

                                val despues = viewModel.cards
                                if (antes[index].isFaceUp == false && despues[index].isFaceUp == true) {
                                    val flippedCards =
                                        despues.filter { it.isFaceUp && !it.isMatched }
                                    if (flippedCards.size == 2) {
                                        val idxs = despues.mapIndexedNotNull { i, c ->
                                            if (c.isFaceUp && !c.isMatched) i else null
                                        }
                                        if (flippedCards[0].imageResId != flippedCards[1].imageResId) {
                                            cartasParaOcultar = Pair(idxs[0], idxs[1])
                                        }
                                    }
                                }
                            }
                        },
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                ) {
                    if (card.isFaceUp || card.isMatched) {
                        Image(
                            painter = painterResource(card.imageResId),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp), // un poco de padding para que no quede pegada al borde
                            contentScale = ContentScale.Fit // mantén proporción y centrado
                        )
                    } else {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.primary
                        ) {}
                    }
                }
            }
        }
    }
}
}

fun columnasPorDificultad(dificultad: String): Int {
    return when (dificultad.lowercase()) {
        "facil" -> 2
        "medio" -> 4
        "dificil" -> 4
        else -> 2
    }
}*/
@Composable
fun GameScreen(
    dificultad: String,
    tipo: String,
    onFinish: (Int) -> Unit,
    viewModel: MemoryViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.startGame(dificultad, tipo)
    }

    val cards = viewModel.cards
    var cartasParaOcultar by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    if (cartasParaOcultar != null) {
        LaunchedEffect(cartasParaOcultar) {
            kotlinx.coroutines.delay(1000L)
            viewModel.ocultarCartasNoEmparejadas(cartasParaOcultar!!.first, cartasParaOcultar!!.second)
            cartasParaOcultar = null
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        val total = cards.size
        val columnas = calcularColumnas(total)
        val filas = (total + columnas - 1) / columnas

        val cardSpacing = 8.dp
        val availableWidth = maxWidth - cardSpacing * (columnas - 1)
        val availableHeight = maxHeight - cardSpacing * (filas - 1)
        val cardSize = minOf(availableWidth / columnas, availableHeight / filas)

        LazyVerticalGrid(
            columns = GridCells.Fixed(columnas),
            horizontalArrangement = Arrangement.spacedBy(cardSpacing),
            verticalArrangement = Arrangement.spacedBy(cardSpacing),
            modifier = Modifier
                .width(cardSize * columnas + cardSpacing * (columnas - 1))
                .height(cardSize * filas + cardSpacing * (filas - 1))
        ) {
            itemsIndexed(cards) { index, card ->
                Card(
                    modifier = Modifier
                        .size(cardSize)
                        .clickable {
                            if (cartasParaOcultar == null) {
                                val antes = viewModel.cards
                                viewModel.voltearCarta(index) { movimientos -> onFinish(movimientos) }

                                val despues = viewModel.cards
                                if (antes[index].isFaceUp == false && despues[index].isFaceUp == true) {
                                    val flippedCards = despues.filter { it.isFaceUp && !it.isMatched }
                                    if (flippedCards.size == 2) {
                                        val idxs = despues.mapIndexedNotNull { i, c -> if (c.isFaceUp && !c.isMatched) i else null }
                                        if (flippedCards[0].imageResId != flippedCards[1].imageResId) {
                                            cartasParaOcultar = Pair(idxs[0], idxs[1])
                                        }
                                    }
                                }
                            }
                        },
                    shape = MaterialTheme.shapes.medium
                ) {
                    if (card.isFaceUp || card.isMatched) {
                        Image(
                            painter = painterResource(card.imageResId),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.primary
                        ) {}
                    }
                }
            }
        }
    }
}

fun calcularColumnas(totalCartas: Int): Int {
    return when {
        totalCartas <= 8 -> 2
        totalCartas <= 16 -> 4
        totalCartas <= 24 -> 6
        totalCartas <= 32 -> 8
        else -> 10
    }
}
