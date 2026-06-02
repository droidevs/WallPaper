package io.droidevs.wallpaper.ui.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import io.droidevs.wallpaper.domain.Album
import io.droidevs.wallpaper.domain.LocalWallpaper
import io.droidevs.wallpaper.ui.placeholder.PlaceholderWallpaperCard
import io.droidevs.wallpaper.ui.loading.placeholder.ShrimmerCard
import io.droidevs.wallpaper.ui.loading.shrimmer.shrimmerCount
import io.droidevs.wallpaper.ui.commons.progress.AnimatedCircularProgress
import io.droidevs.wallpaper.ui.theme.WallPaperTheme
import io.droidevs.wallpaper.ui.viewmodels.state.LocalWallpaperListScreenState
import io.droidevs.wallpaper.ui.viewmodels.state.LoadingMode
import io.droidevs.wallpaper.ui.viewmodels.state.SelectState
import kotlinx.coroutines.delay

@Composable
fun WallpaperStaggeredGrid(
    modifier: Modifier = Modifier,
    state: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    loadingState: LocalWallpaperListScreenState,
    selectState: SelectState,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    wallpapers: List<LocalWallpaper>,
    albums: List<Album>,
    header: (LazyStaggeredGridScope.() -> Unit)? = null,
    emptyContent: (LazyStaggeredGridScope.() -> Unit)? = null,
    showSelection: Boolean = false,
    gridColMinWidthPct: Int = 40,
    roundedCorners: Boolean = true,
    onWallpaperClick: (wallpaper: LocalWallpaper) -> Unit = {},
    onWallpaperSelect : (wallpaper : LocalWallpaper, selected :Boolean) -> Unit,
    onWallpaperDelete : (wallpaper : LocalWallpaper) -> Unit = {},
    onWallpaperMove : (wallpaper: LocalWallpaper, album : Album) -> Unit,
    onWallpaperMoveToNew : (wallpaper: LocalWallpaper, album: String) -> Unit,
) {
    val isRefreshing = loadingState.mode == LoadingMode.Refresh

    val isLoadingMore = loadingState.mode == LoadingMode.Append && !isRefreshing

    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(key1 = isRefreshing, key2 =  isLoadingMore)  {
        if (!isLoadingMore and !isRefreshing) {
            delay(500)
            isLoading.value = false
        }
        else {
            isLoading.value = true
        }
    }

    var gridSize by rememberSaveable { mutableStateOf(IntSize.Zero) }

    val gridWidthDp = gridSize.width.toDp()
    val gridHeightDp = gridSize.height.toDp()

    val layoutDirection = LocalLayoutDirection.current
    val adaptiveMinWidth = getAdaptiveMinWidth(
        contentPadding,
        layoutDirection,
        gridWidthDp,
        gridColMinWidthPct,
    )

    LazyVerticalStaggeredGrid(
        modifier = modifier.onSizeChanged { gridSize = it },
        state = state,
        contentPadding = contentPadding,
        columns = StaggeredGridCells.Adaptive(minSize = adaptiveMinWidth),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        header?.invoke(this)
        if (wallpapers.isEmpty() && !isRefreshing) {
            emptyContent?.invoke(this)
        }
        if (isRefreshing && wallpapers.isEmpty()) {
            items(
                shrimmerCount(
                contentHeight = gridHeightDp,
                shrimmerHeight = 200.dp,
                cols = (gridWidthDp.minus(contentPadding.let {
                    it.calculateStartPadding(layoutDirection) +
                            it.calculateEndPadding(layoutDirection)
                }) / adaptiveMinWidth).toInt()
            )
            ) {
                PlaceholderWallpaperCard()
            }
            return@LazyVerticalStaggeredGrid
        }

        items(wallpapers.size) { index ->
                val wallpaper = wallpapers[index]
                ShrimmerCard(
                    isLoading = isLoading.value && index > wallpapers.size,
                    placeholder = {
                        PlaceholderWallpaperCard()
                    },
                    content = {
                        WallpaperCard(
                            modifier = Modifier.animateItem(),
                            wallpaper = wallpaper!!,
                            albums = albums,
                            blur = true,
                            isSelectionMode = showSelection,
                            isSelected = showSelection && wallpaper.selected,
                            fixedHeight = false,
                            roundedCorners = roundedCorners,
                            onClick = { onWallpaperClick.invoke(wallpaper) },
                            onSelect = { onWallpaperSelect.invoke(wallpaper,it) },
                            onDelete = { onWallpaperDelete.invoke(wallpaper)},
                            onMove = {
                                onWallpaperMove.invoke(wallpaper, it)
                            },
                            onMoveToNew = {
                                onWallpaperMoveToNew.invoke(wallpaper, it)
                            }
                        )
                    }
                )
            }
            item {
                if (isLoadingMore){
                    AnimatedCircularProgress()
                }
        }
    }
}

private fun getAdaptiveMinWidth(
    contentPadding: PaddingValues,
    layoutDirection: LayoutDirection,
    gridWidthDp: Dp,
    gridColMinWidthPct: Int,
): Dp {
    val horizontalPadding = contentPadding.let {
        it.calculateStartPadding(layoutDirection) + it.calculateEndPadding(layoutDirection)
    }
    val availWidth = gridWidthDp - horizontalPadding
    var wDp = availWidth * gridColMinWidthPct / 100
    if (wDp <= 0.dp) {
        wDp = 128.dp
    }
    return wDp
}



private data class GridProps(
    val wallpapers: List<LocalWallpaper>, // Mock or fake data for wallpapers
    val albums : List<Album>,
    val showSelection: Boolean = false,
    val gridColMinWidthPct: Int = 40,
    val roundedCorners: Boolean = true,
    val header: (LazyStaggeredGridScope.() -> Unit)? = null,
    val emptyContent: (LazyStaggeredGridScope.() -> Unit)? = null,
)



private class PreviewGridProps : CollectionPreviewParameterProvider<GridProps>(
    listOf(
        GridProps(
            wallpapers = emptyList(), // Empty wallpapers for testing "emptyContent"
            albums = generateSampleAlbums(3),
            showSelection = false
        ),
        GridProps(
            wallpapers = listOf(LocalWallpaper()), // Single wallpaper
            albums = generateSampleAlbums(3),
            roundedCorners = false,
        ),
        GridProps(
            wallpapers = generateSampleWallpapers(10), // Multiple wallpapers
            albums = generateSampleAlbums(3),
            showSelection = true
        ),
        GridProps(
            wallpapers = emptyList(),
            albums = generateSampleAlbums(3),
            header = { /* Add header composable for testing */ },
            emptyContent = { /* Add emptyContent composable for testing */ }
        ),
    )
)



fun generateSampleWallpapers(count: Int): List<LocalWallpaper> {
    return List(count) { LocalWallpaper(id = it.toString(), name = "Wallpaper $it") }
}

fun generateSampleAlbums(count: Int): List<Album> {
    return List(count) { Album(id = it.toLong(), title = "Album $it")}
}

@Preview
@Composable
private fun PreviewWallpaperStaggeredGrid(
    @PreviewParameter(PreviewGridProps::class) props: GridProps,
) {
    val (
        wallpapers,
        albums,
        showSelection,
        gridColMinWidthPct,
        roundedCorners,
        header,
        emptyContent
    ) = props

    WallPaperTheme {
        WallpaperStaggeredGrid(
            modifier = Modifier.fillMaxSize(),
            wallpapers = wallpapers,
            albums = albums,
            loadingState = LocalWallpaperListScreenState(),
            selectState = SelectState(),
            showSelection = showSelection,
            gridColMinWidthPct = gridColMinWidthPct,
            roundedCorners = roundedCorners,
            header = header,
            emptyContent = emptyContent,
            onWallpaperClick = { /* No-op */ },
            onWallpaperSelect = { _, _ -> /* No-op */ },
            onWallpaperDelete = { /* No-op */ },
            onWallpaperMove = { _, _ -> /* No-op */ },
            onWallpaperMoveToNew = { _, _ -> /* No-op */ }
        )
    }
}

@Composable
fun Int.toDp() : Dp {
    return toDp(LocalDensity.current)
}

fun Int.toDp(density: Density) : Dp {
    return with(density){
        toDp()
    }
}