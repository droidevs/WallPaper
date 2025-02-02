package io.droidevs.wallpaper.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import io.droidevs.wallpaper.domain.Album
import io.droidevs.wallpaper.domain.Wallpaper
import io.droidevs.wallpaper.ui.commons.AddWallpapersActionFlow
import io.droidevs.wallpaper.ui.commons.AppFloatingActionButton
import io.droidevs.wallpaper.ui.commons.BackAppBar
import io.droidevs.wallpaper.ui.commons.ProgressIndicator
import io.droidevs.wallpaper.ui.commons.SelectionMenu
import io.droidevs.wallpaper.ui.commons.WallpaperStaggeredGrid
import io.droidevs.wallpaper.ui.dialogs.PleaseWaitDialog
import io.droidevs.wallpaper.ui.dialogs.auto_wallpaper.ScreenSelectionDialog
import io.droidevs.wallpaper.ui.dialogs.settings.SureDialog
import io.droidevs.wallpaper.ui.layouts.AppLayoutMode
import io.droidevs.wallpaper.ui.layouts.CompactLayoutWithScaffold
import io.droidevs.wallpaper.ui.layouts.DoubleLayoutWithScaffold
import io.droidevs.wallpaper.ui.model.AppBarMenuItem
import io.droidevs.wallpaper.ui.nav.Graph
import io.droidevs.wallpaper.ui.nav.MultiNavigationAppState
import io.droidevs.wallpaper.ui.nav.Screen
import io.droidevs.wallpaper.ui.nav.rememberMultiNavigationAppState
import io.droidevs.wallpaper.ui.system.System
import io.droidevs.wallpaper.ui.system.window
import io.droidevs.wallpaper.ui.viewmodels.event.LoadEvent
import io.droidevs.wallpaper.ui.viewmodels.event.SelectEvent
import io.droidevs.wallpaper.ui.viewmodels.event.SortEvent
import io.droidevs.wallpaper.ui.viewmodels.state.LoadState
import io.droidevs.wallpaper.ui.viewmodels.state.LoadingMode
import io.droidevs.wallpaper.ui.viewmodels.state.SelectState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.material.icons.filled.*
import androidx.navigation.compose.NavHost
import io.droidevs.wallpaper.ui.nav.wallpaperNavGraph
import io.droidevs.wallpaper.ui.nav.wallpaperScreen
import io.droidevs.wallpaper.ui.system.window.AppLayoutInfo
import io.droidevs.wallpaper.ui.viewmodels.event.DeleteEvent


@Composable
fun AlbumScreen(
    navController: NavController,
    album : Album?,
    state : LoadState,
    selectState: SelectState,
    onClick : (wallpaper : Wallpaper) -> Unit,
    onLoadEvent : (LoadEvent) -> Unit,
    onSelectEvent: (SelectEvent) -> Unit,
    onSortEvent: (SortEvent) -> Unit,
    onAddWallpapers: (uris: List<String>) -> Unit,
    onEdit: (editedAlbum: Album) -> Unit,
    onDelete: (DeleteEvent) -> Unit,
    onBack: () -> Unit,
){

    val gridState = remember { mutableStateOf(LazyStaggeredGridState(0,0)) }
    var layout = System.window.state.value.layout
    val layoutMode = layout.appLayoutMode

    val albumNavigationState = rememberMultiNavigationAppState(
        navController = navController as NavHostController,
        startDestination = Screen.Album
    )
    val clickedWallpaper = remember { mutableStateOf<String?>(null) }

    BackHandler(
        enabled = selectState.selectMode,
        onBack = {
            when(layoutMode){
                AppLayoutMode.DOUBLE_BIG -> {
                    if (clickedWallpaper.value != null){
                        clickedWallpaper.value = null
                    }
                    else {
                        onBack()
                    }
                }
                else -> {
                    onBack()
                }
            }
        }
    )
    when(layoutMode) {

        AppLayoutMode.DOUBLE_BIG -> {
            if (clickedWallpaper.value != null){
                useCompactLayout(
                    album = album!!,
                    gridState = gridState.value,
                    state = state,
                    selectState = selectState,
                    onClick = {
                        clickedWallpaper.value = it.id
                    },
                    onLoadEvent = onLoadEvent,
                    onBack = onBack,
                    onDelete = onDelete,
                    onSelectEvent = onSelectEvent,
                    onAddWallpapers = onAddWallpapers,
                    onEdit = onEdit
                )
            }
            else {
                useDoubleLayout(
                    album = album!!,
                    albumNavigationState = albumNavigationState,
                    gridState = gridState.value,
                    state = state,
                    selectState = selectState,
                    onClick = {
                        clickedWallpaper.value = it.id
                    },
                    onLoadEvent = onLoadEvent,
                    onBack = onBack,
                    onDelete = onDelete,
                    onSelectEvent = onSelectEvent,
                    onAddWallpapers = onAddWallpapers,
                    onEdit = onEdit,
                    layout = layout
                )
            }
        }
        else -> {
            useCompactLayout(
                album = album!!,
                gridState = gridState.value,
                state = state,
                selectState = selectState,
                onClick = {
                    albumNavigationState.navigateTo(Screen.Wallpaper) //todo pass id argument
                },
                onLoadEvent = onLoadEvent,
                onBack = onBack,
                onDelete = onDelete,
                onSelectEvent = onSelectEvent,
                onAddWallpapers = onAddWallpapers,
                onEdit = onEdit
            )
        }
    }

}


@Composable
fun useCompactLayout(
    album: Album,
    gridState: LazyStaggeredGridState,
    state: LoadState,
    selectState: SelectState,
    onClick: (wallpaper: Wallpaper) -> Unit,
    onLoadEvent: (LoadEvent) -> Unit,
    onBack: () -> Unit,
    onDelete: (DeleteEvent) -> Unit,
    onSelectEvent: (SelectEvent) -> Unit,
    onAddWallpapers: (uris : List<String>) -> Unit,
    onEdit: (editedAlbum: Album) -> Unit,
){
    CompactLayoutWithScaffold(
        topAppBar = {
            AlbumTopBar(
                album = album,
                onBack = onBack,
                onEdit = onEdit,
                onDelete = {
                    onDelete.invoke(DeleteEvent.DeleteAlbum)
                }
            )
        },
        floatingActionButton = {
            AlbumFloatingActionButton(
                showFAB = true,
                onAddWallpapers = onAddWallpapers
            )
        },
        mainContent = {
            Feed (
                gridState = gridState,
                onClick = onClick,
                loadState = state,
                selectState = selectState,
                onRefresh = { onLoadEvent.invoke(LoadEvent.Refresh) },
                onLoadMore = { onLoadEvent.invoke(LoadEvent.LoadMore) },
                onDelete = { onDelete.invoke(DeleteEvent.DeleteWallpapers(it))},
                onSelect = {
                    id , isSelected ->
                    if (isSelected)
                        onSelectEvent.invoke(SelectEvent.Select(id))
                    else
                        onSelectEvent.invoke(SelectEvent.Deselect(id))
                }
            )
        }
    )
}

@Composable
fun useDoubleLayout(
    album: Album,
    albumNavigationState: MultiNavigationAppState,
    gridState: LazyStaggeredGridState,
    state: LoadState,
    selectState: SelectState,
    onClick: (wallpaper: Wallpaper) -> Unit,
    onLoadEvent: (LoadEvent) -> Unit,
    onBack: () -> Unit,
    onDelete: (DeleteEvent) -> Unit,
    onSelectEvent: (SelectEvent) -> Unit,
    onAddWallpapers: (uris : List<String>) -> Unit,
    onEdit: (editedAlbum: Album) -> Unit,
    layout: AppLayoutInfo
){

    DoubleLayoutWithScaffold(
        appLayoutInfo = layout,
        topAppBar = {
            AlbumTopBar(
                album = album,
                onBack = onBack,
                onEdit = onEdit,
                onDelete = {
                    onDelete.invoke(DeleteEvent.DeleteAlbum)
                }
            )
        },
        leftContent = {
            Feed (
                gridState = gridState,
                onClick = onClick,
                loadState = state,
                selectState = selectState,
                onRefresh = { onLoadEvent.invoke(LoadEvent.Refresh) },
                onLoadMore = { onLoadEvent.invoke(LoadEvent.LoadMore) },
                onDelete = { onDelete.invoke(DeleteEvent.DeleteWallpapers(it))},
                onSelect = {
                        id , isSelected ->
                    if (isSelected)
                        onSelectEvent.invoke(SelectEvent.Select(id))
                    else
                        onSelectEvent.invoke(SelectEvent.Deselect(id))
                }
            )
        },
        rightContent = {
            NavHost (
                navController = albumNavigationState.navController,
                startDestination = Screen.Wallpaper
            ){
                wallpaperScreen()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Feed(
    gridState: LazyStaggeredGridState,
    loadState: LoadState,
    selectState: SelectState,
    onRefresh : () -> Unit,
    onLoadMore : () -> Unit,
    onSelect : (wallpaperId : String, isSelected : Boolean) -> Unit,
    onClick: (wallpaper: Wallpaper) -> Unit,
    onDelete : (wallpapersId : List<String>) -> Unit
){
    val refreshState = rememberPullToRefreshState()
    var selectionMode = remember { mutableStateOf(selectState.selectMode) }

    LaunchedEffect(selectionMode) {
        if (!selectionMode.value){
            var selected = selectState.selectedItems
            selected.forEach {
                onSelect.invoke(it,false)
            }
        }
    }
    if(selectionMode.value){
        SelectionMenu(
            count = selectState.selectedCount,
            onDelete = {
                onDelete.invoke(selectState.selectedItems)
            },
            onClose = {
                selectionMode.value = false
            },
            onShare = {
                //todo
            }
        )
    }
    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        state = refreshState,
        isRefreshing = loadState.mode == LoadingMode.Refresh,
        onRefresh = {
            if (!selectionMode.value) {
                onRefresh()
            }
        },
        indicator = {
            ProgressIndicator()
        },
    ) {
        WallpaperStaggeredGrid(
            modifier = Modifier
                .testTag("home:feed"),
            state = gridState,
            loadingState = loadState,
            selectState = selectState,
            wallpapers = loadState.wallpapers as ArrayList<Wallpaper>,
            header = {

            },
            showSelection = selectionMode.value,
            gridColMinWidthPct = 200,
            roundedCorners = true,
            onWallpaperClick = {
                onClick.invoke(it)
            },
            onWallpaperSelect = {
                    wallpaper, selected ->
                onSelect.invoke(wallpaper.id,selected)
            },
            onWallpaperDelete = {
                onDelete.invoke(listOf(it.id))
            }
        )
    }
}



/*stable ui  */

@Composable
fun AlbumTopBar(
    albumNav: MultiNavigationAppState? = null,
    album: Album,
    onDelete: () -> Unit,
    onEdit: (editedAlbum: Album) -> Unit,
    onBack: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var editedTitle by remember { mutableStateOf(album.title) }

    val menuItems = listOf(
        AppBarMenuItem(
            title = "Edit",
            iconRes = Icons.Default.Edit,
        ),
        AppBarMenuItem(
            title = "Delete",
            iconRes = Icons.Default.Delete,
        )
    )

    BackAppBar(
        title = album.title,
        onBackPressed = onBack,
        menuItems = menuItems,
        onMoreClicked = {
            when (it.title) {
                "Edit" -> showEditDialog = true
                "Delete" -> showDeleteDialog = true
            }
        }
    )

    if (showDeleteDialog) {
        SureDialog(
            onDismiss = { showDeleteDialog = false },
            title = "Delete Album",
            text = "Are you sure you want to delete this album?",
            onConfirm = onDelete
        )
    }

    if (showEditDialog) {
        Dialog(
            onDismissRequest = { showEditDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier.padding(16.dp),
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Edit Album Name")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editedTitle,
                        onValueChange = { editedTitle = it },
                        label = { Text("Album Name") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { showEditDialog = false }) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(onClick = {
                            showEditDialog = false
                            onEdit(album.copy(title = editedTitle))
                        }) {
                            Text("Save")
                        }
                    }
                }
            }
        }
    }
}



/**
 * A specific implementation of [AppFloatingActionButton] for adding wallpapers.
 *
 * @param showFAB Determines if the FAB should be visible.
 * @param isFabExpanded Determines if the FAB should display its text (expanded state).
 * @param onClick Callback for handling FAB click events.
 */
@Composable
fun AlbumFloatingActionButton(
    showFAB: Boolean = true, // Visibility of the FAB.
    isFabExpanded: Boolean = false, // Whether the FAB is in an expanded state.
    onAddWallpapers: (uris : List<String>) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val waiting = remember { mutableStateOf(false) }

    /** Image picker **/
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { uris: List<Uri> ->
            scope.launch(Dispatchers.IO) {
                waiting.value = true
                val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                val uriList = uris.mapNotNull { uri ->
                    context.contentResolver.takePersistableUriPermission(uri, takeFlags)
                    val persistedUriPermissions = context.contentResolver.persistedUriPermissions
                    if (persistedUriPermissions.any { it.uri == uri }) uri.toString() else null
                }
                if (uriList.isNotEmpty()) {
                    withContext(Dispatchers.Main) {
                        onAddWallpapers.invoke(uriList)
                    }
                }
                waiting.value = false
            }
        }
    )

    // Call the general FAB composable with specific parameters for adding wallpapers.
    AppFloatingActionButton(
        showFAB = showFAB, // Pass visibility flag.
        text = "Add Wallpapers", // Text specific to this FAB.
        icon = Icons.Default.Add, // Use the 'Add' icon for this FAB.
        fabColor = Color.Green, // Green color for this FAB.
        isFabExpanded = isFabExpanded, // Pass expansion state.
        onClick = {
            imagePickerLauncher.launch(arrayOf("image/*"))
        }
    )

    if (waiting.value) {
        PleaseWaitDialog(
            onDismissRequest = { waiting.value = false }
        )
    }
}
