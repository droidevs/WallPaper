package io.droidevs.wallpaper.ui.nav.builders

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.droidevs.wallpaper.ui.nav.navigators.Navigator
import io.droidevs.wallpaper.ui.nav.roots.Screen
import io.droidevs.wallpaper.ui.viewmodels.CollectionListViewModel


fun NavGraphBuilder.CollectionListScreen(
    navigator: Navigator
){
    composable<Screen.CollectionList> {
        val viewModel = hiltViewModel<CollectionListViewModel>()
        val state = viewModel.state.collectAsStateWithLifecycle()
        val favoredState = viewModel.favoredState.collectAsStateWithLifecycle()


    }
}