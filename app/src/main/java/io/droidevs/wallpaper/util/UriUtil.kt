package io.droidevs.wallpaper.util

import android.content.Context
import android.net.Uri

fun isValidUri(context: Context, uriString: String?): Boolean {
    if (uriString.isNullOrBlank()) return false
    return try {
        val uri = Uri.parse(uriString)
        uri.scheme != null
    } catch (e: Exception) {
        false
    }
}
