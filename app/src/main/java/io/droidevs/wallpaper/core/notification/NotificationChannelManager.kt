package io.droidevs.wallpaper.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import io.droidevs.wallpaper.R

class NotificationChannelManager(private val context: Context) {
    companion object {
        const val CHANNEL_ID_HOME = "wallpaper_home_channel"
        const val CHANNEL_ID_LOCK = "wallpaper_lock_channel"
        const val CHANNEL_ID_ERROR = "error_channel"
    }

    fun createAllChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            listOf(
                createHomeChannel(),
                createLockChannel(),
                createErrorChannel()
            ).forEach { channel ->
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    private fun createHomeChannel() = NotificationChannel(
        CHANNEL_ID_HOME,
        context.getString(R.string.home_screen_wallpaper_channel),
        NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
        description = context.getString(R.string.home_screen_channel_description)
    }

    private fun createLockChannel() = NotificationChannel(
        CHANNEL_ID_LOCK,
        context.getString(R.string.lock_screen_wallpaper_channel),
        NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
        description = context.getString(R.string.lock_screen_channel_description)
    }

    private fun createErrorChannel() = NotificationChannel(
        CHANNEL_ID_ERROR,
        context.getString(R.string.error_channel),
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        description = context.getString(R.string.error_channel_description)
    }
}