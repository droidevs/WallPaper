package io.droidevs.wallpaper.core.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.hilt.android.qualifiers.ApplicationContext
import io.droidevs.wallpaper.data.repository.AlbumRepository


@HiltWorker
class WallpaperChangerWorker(
    @ApplicationContext context: Context,
    repo: AlbumRepository,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val bitmap = repository.getNextWallpaper()
            wallpaperService.applyWallpaper(bitmap)
            Result.success()
        } catch (e: Exception) {
            Result.retry() // Optional: retry on failure
        }
    }
}
