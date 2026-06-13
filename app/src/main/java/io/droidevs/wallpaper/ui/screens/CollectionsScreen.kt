package io.droidevs.wallpaper.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.grid.items
import io.droidevs.wallpaper.ui.layouts.SomethingWrongLayout
import io.droidevs.wallpaper.ui.commons.CollectionCard
import io.droidevs.wallpaper.ui.commons.CollectionAction
import io.droidevs.wallpaper.ui.commons.TopicCard
import io.droidevs.wallpaper.ui.commons.TrackableAdaptiveCells
import io.droidevs.wallpaper.ui.layouts.CompactLayoutWithScaffold
import io.droidevs.wallpaper.ui.viewmodels.actions.CollectionListScreenAction
import io.droidevs.wallpaper.ui.viewmodels.actions.TopicListScreenAction
import io.droidevs.wallpaper.ui.viewmodels.state.CollectionListScreenState
import io.droidevs.wallpaper.ui.viewmodels.state.LoadingMode
import io.droidevs.wallpaper.ui.viewmodels.state.TopicsState

@Composable
fun Collections(

) {


}

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionsListFeed(
    state: CollectionListScreenState,
    isDouble: Boolean,
    onAction : (CollectionListScreenAction) -> Unit,
){
    CompactLayoutWithScaffold(
        topAppBar = {
            TODO()
        },
        isStandalone = !isDouble
    ) {

        if (state.error != null) {
            SomethingWrongLayout(
                errorMessage = "Something went wrong"
            )
        } else {
            val refreshState = rememberPullToRefreshState()
            PullToRefreshBox(
                modifier = Modifier.fillMaxSize(),
                isRefreshing = state.state == LoadingMode.Refresh,
                onRefresh = {
                    onAction(CollectionListScreenAction.Refresh)
                },
                state = refreshState,
            ) {

                BoxWithConstraints {
                    val minCellSize = if (maxWidth < 600.dp) 180.dp else 250.dp
                    val cells = remember(minCellSize) { TrackableAdaptiveCells(minCellSize) }


                    LaunchedEffect(cells.currentColumnCount) {
                        //todo: report it to viewmodel
                    }

                    val lazyGridState = rememberLazyGridState()

                    // This is the key to detecting "load more"
                    // It's a side-effect that should not run on every recomposition
                    LaunchedEffect(lazyGridState) {
                        // snapshotFlow converts compose state reads into a Flow
                        snapshotFlow { lazyGridState.layoutInfo }
                            .collect { layoutInfo ->
                                val lastVisibleItem =
                                    layoutInfo.visibleItemsInfo.lastOrNull() ?: return@collect

                                // The total number of items in your list
                                val totalItems = layoutInfo.totalItemsCount

                                // A buffer to start loading before the user reaches the absolute end
                                val prefetchDistance = cells.currentColumnCount * 2

                                // If the last visible item's index is close to the end of the list,
                                // and we are not currently loading, and there are more items to load...
                                if (lastVisibleItem.index >= totalItems - 1 - prefetchDistance &&
                                    !state.state.isLoading() &&
                                    !state.endReached
                                ) {
                                    // ...then trigger the load more function!
                                    onAction(CollectionListScreenAction.LoadingMore)
                                }
                            }
                    }
                    // Responsive grid layout
                    LazyVerticalGrid(
                        columns = cells,
                        state = lazyGridState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        items(state.collections.size) { index ->
                            val collection = state.collections[index]
                            CollectionCard(
                                collection = collection,
                                onAction = { action ->
                                    when (action) {
                                        is CollectionAction.Open -> {
                                            onAction(CollectionListScreenAction.CollectionClicked(collection))
                                        }
                                        is CollectionAction.Share -> {
                                            onAction(CollectionListScreenAction.Share(collection))
                                        }
                                        else -> {}
                                    }
                                }
                            )
                        }

                        // Append loading state
                        if (state.state == LoadingMode.Append) {
                            item(span = { GridItemSpan(3) }) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .wrapContentWidth(Alignment.CenterHorizontally),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}
