package io.droidevs.wallpaper.ui.commons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.droidevs.wallpaper.ui.model.collections.CollectionUi
import kotlin.time.Instant

@Composable
fun CollectionCard(
    modifier: Modifier = Modifier,
    collection: CollectionUi,
    onAction: (CollectionAction) -> Unit,
) {
    var showMenu by remember { mutableStateOf(false) }
    var showDetailsDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .clickable { onAction(CollectionAction.Open) }
            .clip(MaterialTheme.shapes.medium),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Cover image with overlay
            AsyncImage(
                model = collection.coverUrl,
                contentDescription = collection.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Dark overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )

            // Title
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart),
                text = collection.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Photo count badge
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomEnd)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                text = "${collection.totalPhotos} photos",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            // 3-dot menu button
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp),
                onClick = { showMenu = true }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu",
                    tint = Color.White
                )

                // Dropdown menu
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("View Details") },
                        onClick = {
                            showMenu = false
                            showDetailsDialog = true
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Share Collection") },
                        onClick = {
                            showMenu = false
                            onAction(CollectionAction.Share(collection.id))
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Add to Favorites") },
                        onClick = {
                            showMenu = false
                            onAction(CollectionAction.ToggleFavorite(collection.id))
                        }
                    )
                }
            }
        }
    }

    // Full details dialog
    if (showDetailsDialog) {
        AlertDialog(
            onDismissRequest = { showDetailsDialog = false },
            title = { Text(collection.title, style = MaterialTheme.typography.headlineSmall) },
            text = {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    AsyncImage(
                        model = collection.coverUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(collection.description, style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Photos: ${collection.totalPhotos}")
                        Text("Updated: ${Instant.fromEpochMilliseconds(collection.updateTime)
                            .toString()
                            .take(10)}")
                    }
                    Spacer(Modifier.height(8.dp))
                    Text("Dimensions: ${collection.coverWidth}x${collection.coverHeight}")
                }
            },
            confirmButton = {
                TextButton(onClick = { showDetailsDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}

// Enhanced actions
sealed class CollectionAction {
    object Open : CollectionAction()
    data class Share(val collectionId: Int) : CollectionAction()
    data class ToggleFavorite(val collectionId: Int) : CollectionAction()
}