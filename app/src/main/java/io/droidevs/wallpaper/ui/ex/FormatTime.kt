package io.droidevs.wallpaper.ui.ex

import android.text.format.DateUtils
import androidx.compose.runtime.Composable

@Composable
fun Long.formatTime(): String {
    val formatted = DateUtils.formatElapsedTime(this / 1000)

    return formatted.substring(0, 5).trimEnd(':')
}