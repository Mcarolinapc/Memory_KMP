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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import org.example.project.model.CardMemory
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.audio.AudioPlayer
import org.example.project.viewModel.MemoryViewModel

@Composable
fun GameScreen(
    dificultad: String,
    tipo: String,
    viewModel: MemoryViewModel,
    onFinish: (Int) -> Unit
) {
    val azulClaro = Color(0xFF4A90E2)
    val blanco = Color.White
    val azulClaroSuave = Color(0xFFB3D4FC)

    LaunchedEffect(Unit) {
        viewModel.startGame(dificultad, tipo)
    }

    val tiempoRestante by viewModel.tiempoRestante.collectAsState()
    val paresEncontrados by viewModel.paresEncontrados.collectAsState()
    val juegoFinalizado by viewModel.juegoFinalizado.collectAsState()
    val cartasParaOcultar = viewModel.cartasParaOcultar

    if (cartasParaOcultar != null) {
        LaunchedEffect(cartasParaOcultar) {
            kotlinx.coroutines.delay(1000L)
            viewModel.ocultarCartasNoEmparejadas()
        }
    }

    if (juegoFinalizado) {
        LaunchedEffect(Unit) {
            onFinish(paresEncontrados)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(blanco)
            .padding(24.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = if (viewModel.musicaActiva) "🎵 Música activada" else "🔇 Música desactivada",
                color = azulClaro,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Switch(
                checked = viewModel.musicaActiva,
                onCheckedChange = { viewModel.toggleMusica() },
                colors = SwitchDefaults.colors(checkedThumbColor = azulClaro)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "⏱ Tiempo restante: $tiempoRestante s",
            fontSize = 18.sp,
            color = azulClaro,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "🧠 Pares encontrados: $paresEncontrados",
            fontSize = 18.sp,
            color = azulClaro,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Cartas
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            val cards = viewModel.cards
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
                                if (viewModel.cartasParaOcultar == null) {
                                    viewModel.voltearCarta(index) { pares -> onFinish(pares) }
                                }
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (card.isFaceUp || card.isMatched) blanco else azulClaroSuave
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (card.isFaceUp || card.isMatched) {
                            Image(
                                painter = painterResource(card.imageResId),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
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

