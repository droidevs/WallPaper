package io.droidevs.wallpaper.infrastructure.datastore

import java.sql.Time

sealed class TimeInterval(val interval: Long) {

    object Off : TimeInterval(0L)

    object EveryMinute : TimeInterval(60 * 1000L) // 1 minute

    object Every5Minute : TimeInterval(5 * 60 * 1000L) // 5 minutes

    object Every15Minute : TimeInterval(15 * 60 * 1000L) // 15 minutes

    object Every30Minute : TimeInterval(30 * 60 * 1000L) // 30 minutes

    object EveryHour : TimeInterval(60 * 60 * 1000L) // 1 hour

    object Every3Hour : TimeInterval(3 * 60 * 60 * 1000L) // 3 hours

    object Every6Hour : TimeInterval(6 * 60 * 60 * 1000L) // 6 hours

    object Every12Hour : TimeInterval(12 * 60 * 60 * 1000L) // 12 hours

    object EveryDay : TimeInterval(24 * 60 * 60 * 1000L) // 1 day

    object Every3Days : TimeInterval(3 * 24 * 60 * 60 * 1000L) // 3 days

    object EveryWeek : TimeInterval(7 * 24 * 60 * 60 * 1000L) // 1 week
}
