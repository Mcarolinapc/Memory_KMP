package org.example.project.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun SelectCategory(
    dificultad: String,
    navigateToGame: (String) -> Unit
) {
    val tipos = listOf("flor", "grupo", "itb")
    var selectedTipo by remember { mutableStateOf<String?>(null) }

    val backgroundColor = Color.White
    val cardColor = Color(0xFFD6EFFF) // Azul claro
    val selectedCardColor = Color(0xFFB3E5FC) // Más oscuro para selección

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(16.dp)
        ) {
            Text(
                text = "Selecciona un tipo",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF1565C0), // Azul fuerte
                modifier = Modifier.padding(bottom = 16.dp)
            )

            tipos.forEach { tipoItem ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { selectedTipo = tipoItem },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedTipo == tipoItem) selectedCardColor else cardColor
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = tipoItem.replaceFirstChar { it.uppercase() },
                        modifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 24.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )
                }
            }

            Button(
                onClick = { selectedTipo?.let { navigateToGame(it) } },
                enabled = selectedTipo != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF64B5F6), // Azul medio
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray
                ),
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Jugar")
            }
        }
    }
}
