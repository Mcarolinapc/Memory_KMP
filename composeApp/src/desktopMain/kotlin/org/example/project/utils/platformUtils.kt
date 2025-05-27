package org.example.project.utils

import androidx.compose.runtime.Composable
import java.awt.Window
import javax.swing.SwingUtilities


import kotlin.system.exitProcess

actual fun exitApp(platformContext: Any?) {
    exitProcess(0)
}