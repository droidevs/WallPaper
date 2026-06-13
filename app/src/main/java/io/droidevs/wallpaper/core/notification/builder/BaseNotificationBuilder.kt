package io.droidevs.wallpaper.core.notification.builder

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import io.droidevs.wallpaper.core.utils.PermissionUtils

abstract class BaseNotificationBuilder(
    protected val context: Context,
    protected val channelId: String,
    protected val notificationId: Int
) {
    companion object {
        private const val TAG = "NotificationBuilder"
    }

    protected abstract fun build(): Notification

    @android.annotation.SuppressLint("MissingPermission")
    fun show() {
        if (shouldShowNotification().not()) return

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, build())
    }

    protected open fun shouldShowNotification(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!PermissionUtils.checkNotificationPermission(context)) {
                Log.i(TAG, "Notification permission not granted, skipping notification")
                return false
            }
        }
        return true
    }

    protected fun createPendingIntent(intent: Intent, requestCode: Int = notificationId): PendingIntent {
        return PendingIntent.getActivity(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    protected fun createBroadcastPendingIntent(intent: Intent, requestCode: Int = notificationId): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}