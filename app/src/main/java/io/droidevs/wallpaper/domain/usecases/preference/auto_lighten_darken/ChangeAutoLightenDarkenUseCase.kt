package io.droidevs.wallpaper.domain.usecases.preference.auto_lighten_darken

import io.droidevs.wallpaper.domain.preferences.AutoLightenDarkenPreference
import javax.inject.Inject

class ChangeAutoLightenDarkenUseCase @Inject constructor(
    private val pref: AutoLightenDarkenPreference,
) {
    suspend operator fun invoke(enabled: Boolean) {
        pref.setEnabled(enabled)
    }
}