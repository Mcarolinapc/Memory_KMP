package org.example.project.utils


import android.app.Activity
import org.example.project.AppContextHolder.context


actual fun exitApp(platformContext: Any?) {
    val activity = context as? Activity
    activity?.finish()
}