package io.droidevs.wallpaper.ui.ex

import android.os.Build
import java.time.Instant
import java.time.OffsetDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.toKotlinInstant

@OptIn(ExperimentalTime::class)
fun IsoToLong(iso: String): Long {

    // Parse the string into an OffsetDateTime (handles timezone offset)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val offsetDateTime = OffsetDateTime.parse(iso)
        // Convert to Instant (UTC)
        val instant: Instant = offsetDateTime.toInstant()

        // Get milliseconds since epoch
        return instant.toEpochMilli()
    } else {
        return kotlin.time.Instant.parse(iso).toEpochMilliseconds()
    }

}