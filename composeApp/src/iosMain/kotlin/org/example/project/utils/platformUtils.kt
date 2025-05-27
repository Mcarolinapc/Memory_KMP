package org.example.project.utils

import androidx.compose.runtime.Composable


actual fun exitApp(platformContext: Any?) {
    // Aquí puedes llamar a exit(0) o similar para iOS nativo
    // Ejemplo:
    kotlin.system.exitProcess(0)
}