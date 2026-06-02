package io.droidevs.wallpaper.data.system

import android.content.Context
import android.content.res.Configuration
import io.droidevs.wallpaper.domain.services.ThemeMonitorService

class ThemeMonitorServiceImpl(
    private val context: Context
) : ThemeMonitorService {
    override suspend fun isNightMode(): Boolean {
        val nightModeFlags =
            context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }
}