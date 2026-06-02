package io.droidevs.wallpaper.data.mappers

import io.droidevs.wallpaper.data.local.EffectEntity
import io.droidevs.wallpaper.domain.model.Effect

fun EffectEntity.toEffect(): Effect = Effect(
    blurValue,
    brightnessValue,
    contrastValue,
    saturationValue,
    hueRedValue,
    hueGreenValue,
    hueBlueValue,
    scaleRedValue,
    scaleGreenValue,
    scaleBlueValue
)

fun Effect.toEntity(id: Int = 0): EffectEntity = EffectEntity(
    id,
    blurValue,
    brightnessValue,
    contrastValue,
    saturationValue,
    hueRedValue,
    hueGreenValue,
    hueBlueValue,
    scaleRedValue,
    scaleGreenValue,
    scaleBlueValue
)
