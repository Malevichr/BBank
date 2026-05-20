package ru.malevichrp.bbank.coreui

import android.app.Activity
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalView

@Composable
fun SecureScreen(enabled: Boolean) {
    val view = LocalView.current

    DisposableEffect(enabled) {
        val window = (view.context as Activity).window

        if (enabled) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }

        onDispose {
            window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }
}