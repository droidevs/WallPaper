package io.droidevs.wallpaper.core.notification

import android.content.Context
import android.graphics.Bitmap
import io.droidevs.wallpaper.core.notification.builder.ErrorNotificationBuilder
import io.droidevs.wallpaper.core.notification.builder.WallpaperNotificationBuilder
import java.io.File

class WallpaperNotificationManager(private val context: Context) {
    private val channelManager = NotificationChannelManager(context)

    init {
        channelManager.createAllChannels()
    }

    fun showErrorNotification(errorMessage: String) {
        ErrorNotificationBuilder(context, errorMessage).show()
    }

    fun showWallpaperChangedNotification(
        isHomeScreen: Boolean,
        file: File,
        bitmap: Bitmap
    ) {
        WallpaperNotificationBuilder(
            context = context,
            isHomeScreen = isHomeScreen,
            wallpaperFile = file,
            wallpaperBitmap = bitmap
        ).show()
    }
}