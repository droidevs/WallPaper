package io.droidevs.wallpaper.ui.commons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.droidevs.wallpaper.domain.LocalWallpaper
import java.text.CharacterIterator
import java.text.StringCharacterIterator

@Composable
fun WallpaperDimensionsText(
    wallpaper: LocalWallpaper,
    displayWidth: Int,
    displayHeight: Int,
    isSelected: Boolean
) {
    val showWarningIndicator = true // TODO(GET from settings)

    Row(
        modifier = Modifier
            .padding(start = 16.dp, top = 4.dp, bottom = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${wallpaper.width ?: 0}x${wallpaper.height ?: 0}, ${wallpaper.size.toSize()}",
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            color = Color.White,
            textDecoration =
                if(isSelected)
                    TextDecoration.Underline
                else
                    TextDecoration.None
        ,
            modifier = Modifier.weight(1f)
        )

        if (showWarningIndicator) {
            when {
                (wallpaper.width ?: 0) > displayWidth || (wallpaper.height
                    ?: 0) > displayHeight -> {
                    Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.size(16.dp)
                    )
                }

                (wallpaper.width ?: 0) < displayWidth || (wallpaper.height
                    ?: 0) < displayHeight -> {
                    Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(16.dp)
                    )
                }

                else -> {

                }
            }
        }
    }
}

fun Int.toSize(): String {
    return toLong().toSize()
}

fun Long.toSize(): String {
    return this.humanReadableByteCountSI()
}

private fun Long.humanReadableByteCountSI(): String {
    var bytes = this
    if (-1000 < bytes && bytes < 1000) {
        return "$bytes B"
    }
    val ci: CharacterIterator = StringCharacterIterator("kMGTPE")
    while (bytes <= -999950 || bytes >= 999950) {
        bytes /= 1000
        ci.next()
    }
    return String.format("%.1f %cB", bytes / 1000.0, ci.current())
}