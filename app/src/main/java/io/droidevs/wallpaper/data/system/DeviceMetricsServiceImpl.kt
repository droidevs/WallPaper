package io.droidevs.wallpaper.data.system

import android.app.KeyguardManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.PowerManager
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.RequiresApi
import io.droidevs.wallpaper.R
import io.droidevs.wallpaper.domain.LocalWallpaper
import io.droidevs.wallpaper.domain.services.DeviceMetricsService
import okhttp3.internal.wait
import javax.inject.Inject


internal class DeviceMetricsServiceImpl @Inject constructor(
    private val context: Context
) : DeviceMetricsService {

    private lateinit var size : Pair<Int, Int>

    override fun getScreenDimensions(): Pair<Int, Int> {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowManager.currentWindowMetrics.bounds.let {
                size = it.width() to it.height()
                size
            }
        } else {
            @Suppress("DEPRECATION")
            val metrics = DisplayMetrics().also {
                windowManager.defaultDisplay.getMetrics(it)
            }
            metrics.widthPixels to metrics.heightPixels
        }
    }

    override fun getScreenDensity(): String {
        return when (context.resources.displayMetrics.densityDpi) {
            DisplayMetrics.DENSITY_LOW -> {
                "ldpi"
            }

            DisplayMetrics.DENSITY_140 -> {
                "ldpi - mdpi"
            }

            DisplayMetrics.DENSITY_MEDIUM -> {
                "mdpi"
            }

            DisplayMetrics.DENSITY_180,
            DisplayMetrics.DENSITY_200,
            DisplayMetrics.DENSITY_220 -> {
                "mdpi - hdpi"
            }

            DisplayMetrics.DENSITY_HIGH -> {
                "hdpi"
            }

            DisplayMetrics.DENSITY_260,
            DisplayMetrics.DENSITY_280,
            DisplayMetrics.DENSITY_300 -> {
                "hdpi - xhdpi"
            }

            DisplayMetrics.DENSITY_XHIGH -> {
                "xhdpi"
            }

            DisplayMetrics.DENSITY_340,
            DisplayMetrics.DENSITY_360,
            DisplayMetrics.DENSITY_400,
            DisplayMetrics.DENSITY_420,
            DisplayMetrics.DENSITY_440 -> {
                "xhdpi - xxhdpi"
            }

            DisplayMetrics.DENSITY_XXHIGH -> {
                "xxhdpi"
            }

            DisplayMetrics.DENSITY_560,
            DisplayMetrics.DENSITY_600 -> {
                "xxhdpi - xxxhdpi"
            }

            DisplayMetrics.DENSITY_XXXHIGH -> {
                "xxxhdpi"
            }

            DisplayMetrics.DENSITY_TV -> {
                "tvdpi"
            }

            else -> context.getString(R.string.unknown)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getRefreshRate(): Float {
        @Suppress("deprecation")
        return context.getSystemService(WindowManager::class.java).defaultDisplay.refreshRate
    }

    override fun getOrientation(): String {
        return when (context.resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> context.resources.getString(R.string.landscape)
            Configuration.ORIENTATION_PORTRAIT -> context.resources.getString(R.string.portrait)
            else -> context.resources.getString(R.string.unknown)
        }
    }

    override fun isLandscape(): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    override fun isPortrait(): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun isDeviceLocked(): Boolean {
        return (context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager).isDeviceLocked
    }

    override fun isDeviceSleeping(): Boolean {
        return (context.getSystemService(Context.POWER_SERVICE) as PowerManager).isInteractive
    }

    override fun isFittingScreen(wallpaper: LocalWallpaper): Boolean {
        return wallpaper.width == size.first && wallpaper.height == size.second
    }
}