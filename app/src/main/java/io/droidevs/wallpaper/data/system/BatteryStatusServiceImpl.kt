package io.droidevs.wallpaper.data.system

import android.content.Context
import android.os.BatteryManager
import io.droidevs.wallpaper.domain.services.BatteryStatusService

class BatteryStatusServiceImpl(private val context: Context) : BatteryStatusService {

    private val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

    override fun getBatteryPercentage(): Int {
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    }

    override fun isLowBattery(): Boolean {
        return getBatteryPercentage() <= LOW_BATTERY_THRESHOLD
    }

    companion object {
        private const val LOW_BATTERY_THRESHOLD = 20
    }
}
