package org.example.project.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(navigateTotype:(String)->Unit) {
    var selecteDificultad: String by remember { mutableStateOf("Selecciona dificultad") }
    var selectedValue by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val dificultades = mapOf(
        "Fácil" to "facil",
        "Media" to "medio",
        "Experto" to "dificil"
    )



    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedButton(onClick = { expanded = true }) {
            Text("Dificultad")
        }
        OutlinedTextField(
            value = selecteDificultad,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .clickable { expanded = true }
                .fillMaxWidth()
                .background(Color.DarkGray)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            dificultades.forEach { (clave, valorInterno) ->
                DropdownMenuItem(text = { Text(clave) }, onClick = {
                    selecteDificultad = clave
                    selectedValue = valorInterno
                    expanded = false
                })
            }
        }
        Button(
            onClick = { navigateTotype(selectedValue) },
            modifier = Modifier.padding(top = 24.dp),
            enabled = selectedValue.isNotEmpty()
        ) {
            Text("Siguiente")
        }
    }
}
