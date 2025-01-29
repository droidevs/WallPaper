package io.droidevs.wallpaper.ui.commons


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.droidevs.wallpaper.domain.Album
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
        /**
         * AddWallpapersActionFlow manages the floating action button (FAB) and the album list bottom sheet.
         * - Clicking the FAB expands it into a list, then transitions smoothly into the bottom sheet.
         * - The FAB disappears when the bottom sheet is visible and reappears when dismissed.
         *
         * @param albums List of albums to display in the bottom sheet.
         * @param onAlbumClick Callback function triggered when an album is clicked.
         * @param onCreateAlbumClick Callback function triggered when the user wants to create a new album.
         */
fun AddWallpapersActionFlow(
    albums: List<Album>,
    onAlbumClick: (Album) -> Unit,
    onCreateAlbumClick: () -> Unit
) {
    // State to manage FAB visibility
    val showFAB = remember { mutableStateOf(true) }

    // State to manage whether the album list bottom sheet should be shown
    val showAlbums = remember { mutableStateOf(false) }

    // State for controlling the modal bottom sheet animation
    val sheetState = rememberModalBottomSheetState()

    // Coroutine scope to handle animations and async tasks
    val coroutineScope = rememberCoroutineScope()

    // Animates FAB size when transitioning between the FAB and the bottom sheet
    val fabSize by animateDpAsState(
        targetValue = if (showAlbums.value) 0.dp else 56.dp, // Shrink FAB when bottom sheet is open
        label = "FAB Size Animation"
    )

    // Animates FAB transparency when transitioning between the FAB and the bottom sheet
    val fabAlpha by animateFloatAsState(
        targetValue = if (showAlbums.value) 0f else 1f, // Hide FAB when bottom sheet is open
        label = "FAB Opacity Animation"
    )

    // Box to position the FAB at the bottom-right corner
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        // Controls the visibility of the FAB with smooth enter/exit animations
        AnimatedVisibility(
            visible = showFAB.value,
            enter = fadeIn() + scaleIn(), // FAB fades in and scales in
            exit = fadeOut() + scaleOut() // FAB fades out and scales out
        ) {
            AddWallpapersFloatingActionButton(
                showFAB = showFAB.value,
                onClick = {
                    showFAB.value = false // Hide FAB when clicked
                    showAlbums.value = true // Show bottom sheet
                },
                fabSize = fabSize,
                fabAlpha = fabAlpha
            )
        }
    }

    // Launch effect to handle the bottom sheet's visibility based on showAlbums state
    LaunchedEffect(showAlbums.value) {
        if (showAlbums.value) {
            coroutineScope.launch { sheetState.show() } // Show the bottom sheet
        } else {
            coroutineScope.launch { sheetState.hide() } // Hide the bottom sheet
        }
    }

    // Bottom sheet component displaying the list of albums
    AlbumListBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            showAlbums.value = false // Hide bottom sheet when dismissed
            showFAB.value = true // Show FAB again
        },
        albums = albums,
        onAlbumClick = onAlbumClick,
        onCreateAlbumClick = onCreateAlbumClick
    )
}

@Composable
        /**
         * AddWallpapersFloatingActionButton represents the floating action button (FAB) that allows users to add wallpapers.
         * It features animations for smooth transitions when expanding or collapsing.
         *
         * @param showFAB Controls whether the FAB should be visible.
         * @param onClick Function to trigger when the FAB is clicked.
         * @param fabSize Animated size of the FAB, reducing to 0 when hidden.
         * @param fabAlpha Animated transparency of the FAB, fading out when hidden.
         */
fun AddWallpapersFloatingActionButton(
    showFAB: Boolean,
    onClick: () -> Unit,
    fabSize: Dp,
    fabAlpha: Float
) {
    // Animated visibility with fade and scale effects
    AnimatedVisibility(
        visible = showFAB,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut()
    ) {
        AppFloatingActionButton(
            onClick = onClick,
            modifier = Modifier
                .size(fabSize) // Apply animated size
                .alpha(fabAlpha), // Apply animated transparency
            fabColor = MaterialTheme.colorScheme.primary,
            icon = Icons.Filled.Add,
            text = "Add Wallpapers"
        )
    }
}

@Preview
@Composable
fun AddWallpapersActionFlowPreview(
    @PreviewParameter(WallpapersPropsPreviewProvider::class) props: WallpapersProps
) {
    AddWallpapersActionFlow(
        albums = props.albums,
        onAlbumClick = {},
        onCreateAlbumClick = {},
    )
}

// Sample data for albums
val sampleAlbums = listOf(
    Album( 1, title = "Landscapes", genre = "Nature", artist = "", releaseYear = 2010),
    Album( 2, title = "Abstract", genre = "Art", artist = "", releaseYear = 2011),
    Album( 3, title = "Animals", genre = "Nature", artist = "", releaseYear = 2012),
    Album(4, title = "Cityscapes", genre = "Nature", artist = "", releaseYear = 2013)
)

// Class to hold state for Preview
data class WallpapersProps(
    val albums: List<Album> = sampleAlbums,
    val showFAB: Boolean = true,
    val showAlbums: Boolean = false
)

// PreviewParameterProvider to provide the WallpapersProps data for testing different states
class WallpapersPropsPreviewProvider : PreviewParameterProvider<WallpapersProps> {
    override val values = sequenceOf(
        WallpapersProps(
            albums = sampleAlbums, // Default albums
            showFAB = true, // FAB is visible
            showAlbums = false // Bottom sheet is hidden
        ),
        WallpapersProps(
            albums = sampleAlbums, // Default albums
            showFAB = false, // FAB is hidden
            showAlbums = true // Bottom sheet is visible
        )
    )
}