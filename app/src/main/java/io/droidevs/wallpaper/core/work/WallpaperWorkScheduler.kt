package io.droidevs.wallpaper.core.work

import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import java.util.concurrent.TimeUnit

object WallpaperWorkScheduler {

    fun schedule(context: Context, intervalHours: Long = 6) {
        val request = PeriodicWorkRequestBuilder<WallpaperChangerWorker>(
            intervalHours, TimeUnit.HOURS
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "AutoWallpaperChange",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}
