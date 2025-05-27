package org.example.project.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/*@Composable
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
}*/


@Composable
fun HomeScreen(navigateTotype: (String) -> Unit) {
    var selecteDificultad by remember { mutableStateOf("Selecciona dificultad") }
    var selectedValue by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val dificultades = mapOf(
        "Fácil" to "facil",
        "Media" to "medio",
        "Experto" to "dificil"
    )

    val primaryBlue = Color(0xFF64B5F6) // Azul medio
    val lightBlue = Color(0xFFD6EFFF)
    val backgroundColor = Color(0xFFF0F8FF) // Azul muy claro

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(24.dp)
            ) {
                Text(
                    text = "Selecciona la dificultad",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF1565C0),
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(lightBlue, shape = RoundedCornerShape(12.dp))
                        .clickable { expanded = true }
                        .padding(16.dp)
                ) {
                    Text(
                        text = selecteDificultad,
                        color = Color.Black
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    dificultades.forEach { (clave, valorInterno) ->
                        DropdownMenuItem(
                            text = { Text(clave) },
                            onClick = {
                                selecteDificultad = clave
                                selectedValue = valorInterno
                                expanded = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (selecteDificultad == clave) lightBlue else Color.Transparent
                                )
                        )
                    }
                }

                Button(
                    onClick = { navigateTotype(selectedValue) },
                    enabled = selectedValue.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryBlue,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray
                    ),
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Siguiente")
                }

            }
        }
    }
}
