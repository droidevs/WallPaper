package io.droidevs.wallpaper.core.notification.builder

import android.app.Notification
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import io.droidevs.wallpaper.R
import io.droidevs.wallpaper.core.notification.NotificationChannelManager
import io.droidevs.wallpaper.core.notification.NotificationConstants
import io.droidevs.wallpaper.core.notification.WallpaperActionReceiver
import java.io.File

class WallpaperNotificationBuilder(
    context: Context,
    private val isHomeScreen: Boolean,
    private val wallpaperFile: File,
    private val wallpaperBitmap: Bitmap
) : BaseNotificationBuilder(
    context,
    if (isHomeScreen) NotificationChannelManager.CHANNEL_ID_HOME else NotificationChannelManager.CHANNEL_ID_LOCK,
    if (isHomeScreen) HOME_NOTIFICATION_ID else LOCK_NOTIFICATION_ID
) {
    companion object {
        const val HOME_NOTIFICATION_ID = 1234
        const val LOCK_NOTIFICATION_ID = 5367
    }

    override fun shouldShowNotification(): Boolean {
        return super.shouldShowNotification()
    }

    override fun build(): Notification {
        val deleteIntent = createDeleteIntent()
        val sendIntent = createSendIntent()

        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_peristyle)
            .setContentText(getContentText())
            .addAction(
                R.drawable.ic_delete,
                context.getString(R.string.delete_current_wallpaper),
                createBroadcastPendingIntent(deleteIntent)
            )
            .addAction(
                R.drawable.ic_share,
                context.getString(R.string.send),
                createPendingIntent(sendIntent)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSilent(true)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(wallpaperBitmap))
            .build()
    }

    private fun createDeleteIntent(): Intent {
        return Intent(context, WallpaperActionReceiver::class.java).apply {
            action = if (isHomeScreen) NotificationConstants.ACTION_DELETE_WALLPAPER_HOME else NotificationConstants.ACTION_DELETE_WALLPAPER_LOCK
            putExtra(NotificationConstants.EXTRA_IS_HOME_SCREEN, isHomeScreen)
            putExtra(NotificationConstants.EXTRA_WALLPAPER_PATH, wallpaperFile.absolutePath)
            putExtra(NotificationConstants.EXTRA_NOTIFICATION_ID, notificationId)
        }
    }

    private fun createSendIntent(): Intent {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            wallpaperFile
        )
        return Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }

    private fun getContentText(): String {
        val screenType = if (isHomeScreen) {
            context.getString(R.string.home_screen)
        } else {
            context.getString(R.string.lock_screen)
        }
        return context.getString(R.string.wallpaper_changed, screenType)
    }
}