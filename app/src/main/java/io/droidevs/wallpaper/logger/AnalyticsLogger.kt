package io.droidevs.wallpaper.logger

import android.app.Activity
import androidx.lifecycle.LifecycleOwner

interface AnalyticsLogger {
    fun registerLifecycleOwner(owner: LifecycleOwner)
}

class AnalyticsLoggerImpl : AnalyticsLogger {

    override fun registerLifecycleOwner(owner: LifecycleOwner) {
        TODO("Not yet implemented")
    }

}