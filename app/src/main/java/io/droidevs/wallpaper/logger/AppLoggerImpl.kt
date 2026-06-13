package io.droidevs.wallpaper.logger

import android.util.Log
import java.util.logging.Logger
import javax.inject.Inject


class AppLoggerImpl @Inject constructor(
    private val analyticsService: AnalyticsLogger? = null
) : AppLogger {

    override fun verbose(tag: String, message: String) {
        if (DEBUG) {
            Log.v(tag, message)
        }
        // Could add log to file here
    }

    override fun debug(tag: String, message: String) {
        if (DEBUG) {
            Log.d(tag, message)
        }
    }

    override fun info(tag: String, message: String) {
        Log.i(tag, message)
        // Could add log to file here
    }

    override fun warning(tag: String, message: String, throwable: Throwable?) {
        Log.w(tag, message, throwable)
        // Could add log to file here
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }


    companion object {
        private val DEBUG = true
    }
}