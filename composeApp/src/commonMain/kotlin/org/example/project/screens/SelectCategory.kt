package org.example.project.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*

@Composable
fun SelectCategory(
    dificultad: String,
    navigateToGame: (String) -> Unit
) {
    val tipos = listOf("flor", "grupo", "itb")
    var selectedTipo by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        LazyColumn {
            items(tipos) { tipoItem ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { selectedTipo = tipoItem },
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = tipoItem.replaceFirstChar { it.uppercase() },
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Button(
            onClick = { selectedTipo?.let { navigateToGame(it) } },
            enabled = selectedTipo != null,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text("Jugar")
        }
    }
}

