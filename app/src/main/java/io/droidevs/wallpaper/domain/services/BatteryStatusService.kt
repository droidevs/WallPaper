package io.droidevs.wallpaper.domain.services

interface BatteryStatusService {
    fun isLowBattery(): Boolean
    fun getBatteryPercentage(): Int
}
