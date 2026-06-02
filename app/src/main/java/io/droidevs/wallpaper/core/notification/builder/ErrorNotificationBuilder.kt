package io.droidevs.wallpaper.core.notification.builder

import android.app.Notification
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import io.droidevs.wallpaper.R
import io.droidevs.wallpaper.core.notification.NotificationChannelManager
import io.droidevs.wallpaper.core.notification.NotificationConstants
import io.droidevs.wallpaper.core.reciever.CopyActionReceiver

class ErrorNotificationBuilder(
    context: Context,
    private val errorMessage: String
) : BaseNotificationBuilder(
    context,
    NotificationChannelManager.CHANNEL_ID_ERROR,
    ERROR_NOTIFICATION_ID
) {
    companion object {
        const val ERROR_NOTIFICATION_ID = 12345
    }

    override fun build(): Notification {
        val copyIntent = Intent(context, CopyActionReceiver::class.java).apply {
            action = NotificationConstants.ACTION_COPY_ERROR_MESSAGE
            putExtra(NotificationConstants.EXTRA_ERROR_MESSAGE, errorMessage)
            putExtra(NotificationConstants.EXTRA_NOTIFICATION_ID, notificationId)
        }

        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_peristyle)
            .setContentTitle(context.getString(R.string.wallpaper_service_crashed))
            .setContentText(errorMessage)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSilent(false)
            .addAction(
                R.drawable.ic_copy_all,
                context.getString(R.string.copy),
                createBroadcastPendingIntent(copyIntent)
            )
            .build()
    }
}