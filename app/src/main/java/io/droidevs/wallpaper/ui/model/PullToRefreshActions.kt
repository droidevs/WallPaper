package io.droidevs.wallpaper.ui.model

import androidx.compose.runtime.Immutable

/**
 * Represents the discrete visual states for our custom indicator.
 * This makes the indicator's logic predictable and easy to manage.
 */
@Immutable
sealed class PullToRefreshAction {
    /** The default, resting state. */
    data object Idle : PullToRefreshAction()

    /** The user is actively pulling down but hasn't reached the threshold yet. */
    data class Pulling(val progress: Float) : PullToRefreshAction()

    /** The user has pulled past the refresh threshold. */
    data object ReachedThreshold : PullToRefreshAction()

    /** The refresh action is in progress. */
    data object Refreshing : PullToRefreshAction()
}