package io.droidevs.wallpaper.ui.ex

import java.util.Locale

fun String.capitalize() = replaceFirstChar { c ->
    if (c.isLowerCase()) {
        c.titlecase(Locale.getDefault())
    } else {
        c.toString()
    }
}