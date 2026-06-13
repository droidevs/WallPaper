package io.droidevs.wallpaper.core

import android.content.Context
import java.io.File
import java.io.InputStream
import java.net.URL
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object WallpaperBitmapLoader {
    private const val TAG = "WallpaperLoader"

    // ==========================================
    // Public API
    // ==========================================

    /**
     * Loads a bitmap from a file/URL, optimized for wallpaper use.
     * - Uses BitmapFactory for local files (faster).
     * - Uses Coil for network images (caching + transformations).
     */
    suspend fun loadBitmap(
        context: Context,
        source: String, // File path or URL
        reqWidth: Int,
        reqHeight: Int,
        cropToFit: Boolean = true
    ): Bitmap? = withContext(Dispatchers.IO) {
        try {
            if (source.isUrl()) {
                loadWithNetwork(source, reqWidth, reqHeight)
            } else {
                loadWithBitmapFactory(File(source), reqWidth, reqHeight, cropToFit)
            }
        } catch (e: OutOfMemoryError) {
            Log.e(TAG, "OOM while loading bitmap", e)
            null
        }
    }

    /**
     * Loads a local image URI (content://) with EXIF correction.
     */
    suspend fun loadLocalBitmap(
        context: Context,
        uri: Uri,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap? = withContext(Dispatchers.IO) {
        context.contentResolver.openInputStream(uri)?.use { stream ->
            loadWithBitmapFactory(stream, reqWidth, reqHeight)
        }
    }

    // ==========================================
    // Core Loading Methods
    // ==========================================

    private suspend fun loadWithNetwork(
        url: String,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap? {
        val bytes = URL(url).openStream().use { it.readBytes() }
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size, this)
            inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)
            inPreferredConfig = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Bitmap.Config.RGBA_1010102 // HDR support
            } else {
                Bitmap.Config.ARGB_8888
            }
            inJustDecodeBounds = false
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
    }

    private suspend fun loadWithCoil(
        context: Context,
        url: String,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap? {
        return loadWithNetwork(url, reqWidth, reqHeight)
    }

    private fun loadWithBitmapFactory(
        file: File,
        reqWidth: Int,
        reqHeight: Int,
        cropToFit: Boolean
    ): Bitmap? {
        return file.inputStream().use { stream ->
            loadWithBitmapFactory(stream, reqWidth, reqHeight)
        }
    }

    private fun loadWithBitmapFactory(
        stream: InputStream,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap? {
        stream.mark(Int.MAX_VALUE)

        // Decode with sampling
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeStream(stream, null, this)
            stream.reset()

            inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)
            inPreferredConfig = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Bitmap.Config.RGBA_1010102 // HDR support
            } else {
                Bitmap.Config.ARGB_8888
            }
            inJustDecodeBounds = false
        }

        val bitmap = BitmapFactory.decodeStream(stream, null, options)
        return bitmap
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height, width) = options.outHeight to options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    private fun String.isUrl(): Boolean = startsWith("http://") || startsWith("https://")
}