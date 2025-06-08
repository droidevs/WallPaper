package io.droidevs.wallpaper.ui.commons

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.droidevs.wallpaper.R

/**
 * A styled selection bar that appears when items are selected.
 *
 * @param selectedItemCount The number of items currently selected. The bar is only visible if this is > 0.
 * @param onClearSelection Lambda to be invoked when the close/clear button is clicked.
 * @param onDelete Lambda to be invoked when the delete action is clicked.
 * @param modifier The modifier to be applied to the surface of the bar.
 */
@Composable
fun StyledSelectBar(
    selectedItemCount: Int,
    onClearSelection: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Animate the visibility of the bar based on the selection count
    AnimatedVisibility(
        visible = selectedItemCount > 0,
        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            // Use a distinct color to stand out from the main app bar
            color = MaterialTheme.colorScheme.secondaryContainer,
            tonalElevation = 3.dp
        ) {
            Row(
                modifier = Modifier
                    .height(56.dp) // Standard height for app bars
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left side: Close button and count
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onClearSelection) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear Selection"
                        )
                    }

                    Spacer(Modifier.width(8.dp))

                    Text(
                        // Use pluralStringResource for correct grammar ("1 item", "2 items")
                        text = pluralStringResource(
                            // You need to define this in your strings.xml
                            id = R.plurals.selected_items_count,
                            count = selectedItemCount,
                            selectedItemCount
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Right side: Action buttons
                Row {
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Selected Items"
                        )
                    }
                    // You can add more action icons here
                    // IconButton(onClick = { /* ... */ }) { ... }
                }
            }
        }
    }
}

// Preview for the component in isolation
@Preview(showBackground = true)
@Composable
fun StyledSelectBarPreview() {
    StyledSelectBar(
        selectedItemCount = 3,
        onClearSelection = {},
        onDelete = {}
    )
}