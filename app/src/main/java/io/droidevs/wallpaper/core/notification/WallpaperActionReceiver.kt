package io.droidevs.wallpaper.core.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class WallpaperActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Minimal no-op receiver to satisfy notification actions.
        Log.d("WallpaperActionReceiver", "Received action: ${intent.action}")
    }
}

