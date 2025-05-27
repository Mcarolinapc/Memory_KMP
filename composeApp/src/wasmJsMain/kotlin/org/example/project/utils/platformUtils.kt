package org.example.project.utils


import androidx.compose.runtime.Composable
import kotlinx.browser.window

import kotlinx.browser.window

actual fun exitApp(platformContext: Any?) {
    window.location.href = "about:blank"
}