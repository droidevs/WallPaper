package io.droidevs.wallpaper.ui.commons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * A composable function for rendering a customizable floating action button (FAB) with animated visibility.
 *
 * @param showFAB Determines if the FAB should be visible.
 * @param text The text displayed on the FAB when expanded.
 * @param icon The icon displayed on the FAB.
 * @param fabColor The background color of the FAB.
 * @param isFabExpanded Determines if the FAB should display its text (expanded state).
 * @param onClick Callback for handling FAB click events.
 * @param contentPadding Padding applied around the FAB.
 */
@Composable
fun AppFloatingActionButton(
    showFAB: Boolean = true, // Visibility of the FAB.
    text: String = "", // Text displayed on the FAB when expanded.
    icon: ImageVector = Icons.Default.MoreVert, // Default icon for the FAB.
    fabColor: Color, // Background color of the FAB.
    isFabExpanded: Boolean = false, // Whether the FAB is in an expanded state.
    onClick: () -> Unit, // Action to perform when the FAB is clicked.
    contentPadding: Dp = 16.dp, // Padding around the FAB.
) {
    // Animates the visibility of the FAB.
    AnimatedVisibility(
        visible = showFAB, // FAB is visible based on this flag.
        enter = slideInVertically( // Animation for entering the screen.
            initialOffsetY = { it / 2 } // Starts sliding in from half its height.
        ),
        exit = slideOutVertically( // Animation for exiting the screen.
            targetOffsetY = { it / 2 } // Slides out to half its height.
        ),
    ) {
        // The floating action button, can be expanded to show text and an icon.
        ExtendedFloatingActionButton(
            modifier = Modifier.padding(contentPadding), // Apply padding around the FAB.
            containerColor = fabColor, // Background color of the FAB.
            onClick = onClick, // Trigger the click callback.
            expanded = isFabExpanded, // Determines if the FAB shows text or just the icon.
            icon = {
                // Icon displayed on the FAB.
                Icon(
                    imageVector = icon, // Use the provided icon.
                    contentDescription = "", // No description for accessibility here.
                )
            },
            text = {
                // Text displayed on the FAB when expanded.
                Text(
                    text = text, // Use the provided text.
                )
            },
        )
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
fun AddWallpapersFloatingActionButton(
    showFAB: Boolean = true, // Visibility of the FAB.
    isFabExpanded: Boolean = false, // Whether the FAB is in an expanded state.
    onClick: () -> Unit, // Action to perform when the FAB is clicked.
) {
    // Call the general FAB composable with specific parameters for adding wallpapers.
    AppFloatingActionButton(
        showFAB = showFAB, // Pass visibility flag.
        text = "Add Wallpapers", // Text specific to this FAB.
        icon = Icons.Default.Add, // Use the 'Add' icon for this FAB.
        fabColor = Color.Green, // Green color for this FAB.
        isFabExpanded = isFabExpanded, // Pass expansion state.
        onClick = onClick // Pass the click action.
    )
}



// Define a data class to hold the props
data class FloatingActionButtonProps(
    val showFAB: Boolean,
    val text: String,
    val icon: ImageVector,
    val fabColor: Color,
    val isFabExpanded: Boolean,
    val contentPadding: Float
)

// Implement a PreviewParameterProvider for FloatingActionButtonProps
class FloatingActionButtonPropsProvider :
    CollectionPreviewParameterProvider<FloatingActionButtonProps>(
        listOf(
            FloatingActionButtonProps(
                showFAB = true,
                text = "Add Wallpapers",
                icon = Icons.Default.Add,
                fabColor = Color.Green,
                isFabExpanded = false,
                contentPadding = 16f
            ),
            FloatingActionButtonProps(
                showFAB = true,
                text = "More Options",
                icon = Icons.Default.MoreVert,
                fabColor = Color.Blue,
                isFabExpanded = true,
                contentPadding = 24f
            ),
            FloatingActionButtonProps(
                showFAB = false,
                text = "Hidden FAB",
                icon = Icons.Default.MoreVert,
                fabColor = Color.Red,
                isFabExpanded = false,
                contentPadding = 16f
            )
        )
    )

// Preview composable for AppFloatingActionButton
@Preview(showBackground = true)
@Composable
fun PreviewAppFloatingActionButton(
    @PreviewParameter(FloatingActionButtonPropsProvider::class) props: FloatingActionButtonProps
) {
    AppFloatingActionButton(
        showFAB = props.showFAB,
        text = props.text,
        icon = props.icon,
        fabColor = props.fabColor,
        isFabExpanded = props.isFabExpanded,
        onClick = {}, // No-op click action for preview
        contentPadding = props.contentPadding.dp
    )
}

// Preview composable for AddWallpapersFloatingActionButton
@Preview(showBackground = true)
@Composable
fun PreviewAddWallpapersFloatingActionButton() {
    AddWallpapersFloatingActionButton(
        showFAB = true,
        isFabExpanded = true,
        onClick = {} // No-op click action for preview
    )
}