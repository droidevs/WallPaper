package io.droidevs.wallpaper.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.room.util.query
import io.droidevs.wallpaper.ui.layouts.SomethingWrongLayout
import io.droidevs.wallpaper.data.model.AlbumEntity
import io.droidevs.wallpaper.domain.pager.PaginationState
import io.droidevs.wallpaper.ui.bottomsheet.AdaptiveBottomSheet
import io.droidevs.wallpaper.ui.commons.AlbumItem
import io.droidevs.wallpaper.ui.commons.AppSearchBar
import io.droidevs.wallpaper.ui.commons.ClickableSearchBar
import io.droidevs.wallpaper.ui.commons.EmptyListMessage
import io.droidevs.wallpaper.ui.commons.StyledSelectBar
import io.droidevs.wallpaper.ui.commons.WallpaperAlbumAction
import io.droidevs.wallpaper.ui.commons.WallpaperAlbumCard
import io.droidevs.wallpaper.ui.commons.progress.ProgressIndicator
import io.droidevs.wallpaper.ui.model.albums.AlbumUi
import io.droidevs.wallpaper.ui.viewmodels.actions.AlbumListScreenAction
import io.droidevs.wallpaper.ui.viewmodels.actions.AlbumListScreenAction.*
import io.droidevs.wallpaper.ui.viewmodels.state.AlbumListScreenState
import io.droidevs.wallpaper.ui.viewmodels.state.LoadingMode
import io.droidevs.wallpaper.ui.window.LocalWindow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumListScreen(
    state: AlbumListScreenState,
    onAction: (AlbumListScreenAction) -> Unit,
) {
    var layoutMode = LocalWindow.current.layoutMode

    if (layoutMode.isSplitScreen()) {
        // TODO()
    } else if (layoutMode.isSplitFoldable()){
        // TODO()
    } else {
        // TODO()
    }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    // Determine if we are in select mode by checking if any item is selected.
    // This is more robust than a separate boolean state.
    val isSelectMode by remember(state.albums) {
        derivedStateOf { state.albums.any { it.isSelected } }
    }

    val selectedCount = state.albums.count { it.isSelected }


    Scaffold(
        topBar = {

            if (isSelectMode) {
                StyledSelectBar(
                    selectedItemCount = selectedCount,
                    onClearSelection = {
                        // Unselect all items
                        onAction(DeselectAll)
                    },
                    onDelete = {
                        // Delete selected items
                        onAction(DeleteAllSelected)
                    }
                )
            } else {
                // Conditionally display the TopAppBar or the SearchBar based on the state
                if (state.isSearchActive || (!state.isSearchActive && state.searchQuery.isNotEmpty())) {
                    // --- ACTIVE SEARCH BAR ---
                    // This is shown when the user clicks the search icon
                    ClickableSearchBar(
                        query = state.searchQuery,
                        onSearchClicked = {
                            onAction(OnSearchAction(state.searchQuery))
                        }
                    )

                } else {
                    // --- DEFAULT TOP APP BAR ---
                    // This is the initial state, showing a title and a search icon
                    TopAppBar(
                        title = { Text("Albums") },
                        actions = {
                            IconButton(onClick = {
                                // This is the key change: send an action to the ViewModel
                                // to activate the search mode.
                                onAction(SearchActiveChanged(true))
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Search Albums"
                                )
                            }
                            // You can add other icons here, e.g., for filtering or settings
                        }
                    )
                }
            }
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.state.isLoading() && state.albums.isEmpty() -> {
                    ProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                !state.state.isLoading() && state.albums.isEmpty() && state.searchQuery.isNotEmpty() -> {
                    EmptyListMessage(
                        title = "Try Another Keyword",
                        subtitle = "No albums found for \"${state.searchQuery}\"",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                !state.state.isLoading() && state.albums.isEmpty() -> {
                    EmptyListMessage(
                        title = "Empty Content",
                        subtitle = "No albums available. Pull to refresh.",
                        //icon = Icons.Filled.Image,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                else -> {

                    AlbumGrid(
                        isSelectMode = isSelectMode,
                        state = state,
                        onAction = onAction
                    )
                }
            }

            state.error?.let { errorMessage ->
                SomethingWrongLayout(
                    errorMessage = "Something went wrong",
                    onCancel = {
                        onAction(NavigateBack)
                    },
                    onRetry = {
                        onAction(RefreshAlbums)
                    }
                )
            }
        }
    }
}


@Composable
fun AlbumListFeed(
    state: AlbumListScreenState,

){

}

// The AlbumGrid composable remains unchanged.
@Composable
fun AlbumGrid(
    isSelectMode: Boolean,
    state: AlbumListScreenState,// Use your actual AlbumEntity
    onAction: (AlbumListScreenAction) -> Unit,
    modifier: Modifier = Modifier
) {

    val lazyGridState = rememberLazyGridState()

    // This is the key to detecting "load more"
    // It's a side-effect that should not run on every recomposition
    LaunchedEffect(lazyGridState) {
        // snapshotFlow converts compose state reads into a Flow
        snapshotFlow { lazyGridState.layoutInfo }
            .collect { layoutInfo ->
                val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull() ?: return@collect

                // The total number of items in your list
                val totalItems = layoutInfo.totalItemsCount

                // A buffer to start loading before the user reaches the absolute end
                val prefetchDistance = 5

                // If the last visible item's index is close to the end of the list,
                // and we are not currently loading, and there are more items to load...
                if (lastVisibleItem.index >= totalItems - 1 - prefetchDistance &&
                    !state.state.isLoading() &&
                    !state.endReached
                ) {
                    // ...then trigger the load more function!
                    onAction(LoadMoreAlbums)
                }
            }
    }
    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Adaptive(minSize = 128.dp), // Adjust minSize as needed
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        itemsIndexed(
            items = state.albums,
            key = { _, album -> album.id } // Replace with your album's unique ID
        ) { index, album ->
            WallpaperAlbumCard( // Assuming you have an AlbumItemCard composable
                isSelectMode = isSelectMode,
                album = album,
                onActionSelected = { action ->
                    when (action) {
                        is WallpaperAlbumAction.OpenAction -> {
                            onAction(ClickAlbum(album))
                        }

                        WallpaperAlbumAction.DeleteAction -> {
                            onAction(DeleteAlbum(album))
                        }

                        WallpaperAlbumAction.EditAction -> onAction(EditAlbum(album))
                        WallpaperAlbumAction.DeselectAction -> onAction(SelectAlbum(album))
                        WallpaperAlbumAction.SelectAction -> onAction(DeselectAlbum(album))
                    }
                }
            )
        }

        if (state.state == LoadingMode.Append) {
            item(span = { GridItemSpan(maxLineSpan) }) { // Make it span the full width
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}