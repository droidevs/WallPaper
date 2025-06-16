package io.droidevs.wallpaper.ui.nav.builders

import androidx.annotation.StringRes
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.droidevs.bmicalc.ui.layouts.SomethingWrongLayout
import io.droidevs.bmicalc.ui.utils.ObserveAsEvents
import io.droidevs.wallpaper.R
import io.droidevs.wallpaper.ui.nav.navigators.Navigator
import io.droidevs.wallpaper.ui.nav.roots.Screen.*
import io.droidevs.wallpaper.ui.screens.AlbumListScreen
import io.droidevs.wallpaper.ui.snackbar.SnackBarAction
import io.droidevs.wallpaper.ui.snackbar.SnackBarController
import io.droidevs.wallpaper.ui.snackbar.SnackBarEvent
import io.droidevs.wallpaper.ui.viewmodels.AlbumListViewModel
import io.droidevs.wallpaper.ui.viewmodels.actions.AlbumListScreenAction
import io.droidevs.wallpaper.ui.viewmodels.events.AlbumListScreenEvent


fun NavGraphBuilder.albumListScreen(
    navigator: Navigator
){
    composable<Albums> {

        val viewModel: AlbumListViewModel = hiltViewModel()

        val state by viewModel.state.collectAsState()

        ObserveAsEvents(
            flow = viewModel.event
        ) { event ->
            when(event){
                is AlbumListScreenEvent.NavigateToAlbumDetail -> {
                    navigator.navigateTo(Album(event.albumId))
                }
                is AlbumListScreenEvent.NavigateToAlbumEdit -> {
                    navigator.navigateTo(AlbumEdit(event.albumId))
                }
                else -> {
                    SnackBarController.sendEvent(
                        SnackBarEvent(
                            message = event.toSnackbarMessageResId(),
                            action = SnackBarAction(
                                name = "Retry",
                                onAction = {
                                    viewModel.onAction(AlbumListScreenAction.RefreshAlbums)
                                }
                            )
                        )
                    )
                }
            }
        }

        if (state.error != null){
            SomethingWrongLayout(
                errorMessage = "Something went wrong on our end please report it to us",
                onRetry = {
                    viewModel.onAction(AlbumListScreenAction.RefreshAlbums)
                }
            )
        } else {
            AlbumListScreen(
                state = state,
                onAction = viewModel::onAction
            )
        }
    }
}

/**
 * Maps an [AlbumListScreenEvent] to a corresponding string resource ID
 * for displaying in a Snackbar.
 *
 * @return The integer resource ID (@StringRes) of the message.
 */
@StringRes
fun AlbumListScreenEvent.toSnackbarMessageResId(): Int {
    return when (this) {
        is AlbumListScreenEvent.AlbumDeletedSuccessfully -> R.string.album_deleted_successfully
        is AlbumListScreenEvent.AlbumDeleteFailed -> R.string.album_delete_failed
        else -> { throw IllegalArgumentException("Unsupported event type: $this") }
    }
}