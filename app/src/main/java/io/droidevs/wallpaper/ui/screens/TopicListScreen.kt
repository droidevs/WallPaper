package io.droidevs.wallpaper.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.droidevs.wallpaper.ui.layouts.SomethingWrongLayout
import io.droidevs.wallpaper.ui.commons.AppHorizontalPager
import io.droidevs.wallpaper.ui.commons.TopicCard
import io.droidevs.wallpaper.ui.commons.TrackableAdaptiveCells
import io.droidevs.wallpaper.ui.layouts.CompactLayoutWithScaffold
import io.droidevs.wallpaper.ui.layouts.DoubleFoldedLayout
import io.droidevs.wallpaper.ui.layouts.DoubleLayoutWithScaffold
import androidx.compose.material.icons.filled.Check
import io.droidevs.wallpaper.ui.model.TopicUi
import io.droidevs.wallpaper.ui.viewmodels.actions.TopicListScreenAction
import io.droidevs.wallpaper.ui.viewmodels.events.TopicListScreenEvents
import io.droidevs.wallpaper.ui.viewmodels.state.LoadingMode
import io.droidevs.wallpaper.ui.viewmodels.state.TopicsState
import io.droidevs.wallpaper.ui.window.LocalWindow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun TopicListScreen(
    state: TopicsState,
    events: SharedFlow<TopicListScreenEvents>,
    onAction: (TopicListScreenAction) -> Unit,
    modifier: Modifier = Modifier
) {

    var layoutMode = LocalWindow.current.layoutMode
    var topic by remember { mutableStateOf<TopicUi?>(null) }

    if (topic != null){
        if (layoutMode.isSplitScreen()){
            DoubleFoldedLayout(
                mainPanel = {
                    TopicListFeed(
                        state = state,
                        isDouble = true,
                        onAction = onAction
                    )
                },
                detailsPanel = {
                    TODO()
                }
            )
        } else if (layoutMode.isSplitFoldable()){
            DoubleLayoutWithScaffold(
                leftContent = {
                    TopicListFeed(
                        state = state,
                        isDouble = true,
                        onAction = onAction
                    )
                },
                rightContent = {
                    TODO()
                }
            )
        } else {
            TopicListFeed(
                state = state,
                isDouble = false,
                onAction = onAction
            )
        }
    } else {
        TopicListFeed(
            state = state,
            isDouble = false,
            onAction = onAction
        )
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicListFeed(
    state: TopicsState,
    isDouble: Boolean,
    onAction : (TopicListScreenAction) -> Unit,
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
            AppHorizontalPager(
                pageCount = 2,
                coroutineScope = rememberCoroutineScope()
            ){ page ->
                when (page) {
                    else -> io.droidevs.wallpaper.ui.commons.TabItem(
                        title = "Tab $page",
                        icon = androidx.compose.material.icons.Icons.Default.Check,
                        content = {}
                    )
                }
            }
            val refreshState = rememberPullToRefreshState()
            PullToRefreshBox(
                modifier = Modifier.fillMaxSize(),
                isRefreshing = state.state == LoadingMode.Refresh,
                onRefresh = {
                    onAction(TopicListScreenAction.Refresh)
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
                                    onAction(TopicListScreenAction.LoadingMoreTopics)
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

                        items(state.topics.size) { index ->
                            val topic = state.topics[index]
                            TopicCard(
                                topic = topic,
                                onClick = { onAction(TopicListScreenAction.TopicClicked(topic)) }
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
