package io.droidevs.wallpaper.ui.nav.builders

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import io.droidevs.bmicalc.ui.utils.ObserveAsEvents
import io.droidevs.wallpaper.data.model.SearchScreenType
import io.droidevs.wallpaper.ui.nav.navigators.Navigator
import io.droidevs.wallpaper.ui.nav.roots.Screen
import io.droidevs.wallpaper.ui.screens.SearchScreen
import io.droidevs.wallpaper.ui.viewmodels.SearchViewModel
import io.droidevs.wallpaper.ui.viewmodels.events.SearchScreenEvent



fun NavGraphBuilder.searchScreen(
    navigator: Navigator
) {

    composable<Screen.Search> { backstackEntry->
        var search = backstackEntry.toRoute<Screen.Search>()

        var viewModel = hiltViewModel<SearchViewModel>()

        val state = viewModel.state.collectAsStateWithLifecycle()

        ObserveAsEvents(
            flow = viewModel.event,
        ) { event ->
            when(event){
                SearchScreenEvent.NavigateBack -> {
                    navigator.navigateUp()
                }
                is SearchScreenEvent.Search -> {
                    navigator.navigateTo(
                        when(event.screenType){
                            SearchScreenType.AlbumList -> TODO()
                            SearchScreenType.LocalWallpaperList -> TODO()
                            SearchScreenType.CollectionList -> TODO()
                            SearchScreenType.FavoritesList -> TODO()
                            SearchScreenType.TopicList -> TODO()
                            SearchScreenType.All -> TODO()
                        }
                    )
                }
            }
        }

        SearchScreen(
            state = state.value,
            onAction = viewModel::onAction,
        )
    }
}