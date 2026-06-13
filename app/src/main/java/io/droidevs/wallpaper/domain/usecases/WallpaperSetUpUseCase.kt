package io.droidevs.wallpaper.domain.usecases

import io.droidevs.wallpaper.domain.model.ApplyEffectData
import io.droidevs.wallpaper.domain.model.WallpaperSetUpData
import io.droidevs.wallpaper.domain.preferences.WallpaperEffectsPreference
import io.droidevs.wallpaper.domain.services.DeviceMetricsService
import io.droidevs.wallpaper.domain.services.ThemeMonitorService
import io.droidevs.wallpaper.domain.services.WallpaperSetUpService
import javax.inject.Inject

class WallpaperSetUpUseCase @Inject constructor(
    private val deviceMetricsService: DeviceMetricsService,
    private val effectPrefs: WallpaperEffectsPreference,
    private val themeMonitor: ThemeMonitorService,
    private val applyEffectUseCase: ApplyEffectUseCase,
    private val wallpaperSetUpService: WallpaperSetUpService,
) : SuspendingUseCase<WallpaperSetUpData, Boolean>(), 
    WallpaperEffectsPreference by effectPrefs, 
    ThemeMonitorService by themeMonitor, 
    WallpaperSetUpService by wallpaperSetUpService, 
    DeviceMetricsService by deviceMetricsService {

    override suspend fun execute(parameters: WallpaperSetUpData): Boolean {
        val processedImage = if (parameters.effect != null) {
            applyEffectUseCase(ApplyEffectData(parameters.image, parameters.effect))
        } else {
            parameters.image
        }
        
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            wallpaperSetUpService.applyWallpaper(processedImage, parameters.screen, parameters.dimens)
        } else {
            wallpaperSetUpService.applyWallpaper(processedImage, parameters.dimens)
        }
    }
}