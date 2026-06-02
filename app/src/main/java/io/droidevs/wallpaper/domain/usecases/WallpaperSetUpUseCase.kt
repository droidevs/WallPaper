package io.droidevs.wallpaper.domain.usecases


import io.droidevs.wallpaper.domain.model.WallpaperSetUpData
import io.droidevs.wallpaper.domain.preferences.WallpaperEffectsPreference
import io.droidevs.wallpaper.domain.services.DeviceMetricsService
import io.droidevs.wallpaper.domain.services.ImageResizerService
import io.droidevs.wallpaper.domain.services.ThemeMonitorService
import io.droidevs.wallpaper.domain.services.WallpaperSetUpService

class WallpaperSetUpUseCase(
    private val deviceMetricsService: DeviceMetricsService,
    private val effectPrefs : WallpaperEffectsPreference,
    private val themeMonitor: ThemeMonitorService,
    private val applyEffectUseCase: ApplyEffectUseCase,
    private val wallpaperSetUpService: WallpaperSetUpService,
) : SuspendingUseCase<WallpaperSetUpData, > , WallpaperEffectsPreference by effectPrefs, ThemeMonitorService by themeMonitor , WallpaperSetUpService by wallpaperSetUpService, DeviceMetricsService by deviceMetricsService, ImageResizerService by imageResizer{


}