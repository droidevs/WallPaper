package io.droidevs.wallpaper.ui.commons

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.droidevs.wallpaper.ui.model.albums.AlbumUi


@Composable
fun WallpaperAlbumCard(
    album: AlbumUi,
    onActionSelected: (WallpaperAlbumAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val isHovered by interactionSource.collectIsHoveredAsState()
    val elevation = animateDpAsState(
        if (isHovered) 8.dp else 4.dp,
        label = "cardElevation"
    )

    // The list of actions to show in the menu
    val actions = listOf(
        WallpaperAlbumAction.SelectAction,
        WallpaperAlbumAction.OpenAction,
        WallpaperAlbumAction.EditAction,
        WallpaperAlbumAction.DeleteAction
    )

    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var itemHeight by remember { mutableStateOf(0.dp) }

    val density = LocalDensity.current


    Card(
        modifier = modifier
            .onSizeChanged {
                itemHeight = with(density){
                    it.height.toDp()
                }
            }
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(
                    bounded = false,
                    radius = 24.dp
                )
            ) {
                onActionSelected(WallpaperAlbumAction.OpenAction)
            }
            .alpha(if (pressed) 0.9f else 1f),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation.value,
            pressedElevation = 2.dp
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .indication(interactionSource, indication = LocalIndication.current)
                .pointerInput(true){
                    detectTapGestures(
                        onPress = {
                            var press = PressInteraction.Press(it)
                            interactionSource.emit(press)
                            tryAwaitRelease()
                            interactionSource.emit(PressInteraction.Release(press))
                        }
                    )
                }.padding(12.dp)
        ) {
            // Main content
            Column {
                // Image Preview
                AsyncImage(
                    model = album.coverImageUrl,
                    contentDescription = "Album preview",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                )

                Row {
                    // Details Section
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(
                            text = album.title,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "${album.total} wallpapers",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }

                    Box {
                        IconButton(
                            modifier = Modifier.align(Alignment.TopEnd),
                            onClick = {
                                isContextMenuVisible = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More Actions"
                            )
                        }

                        // The DropdownMenu is a sibling to the IconButton inside the Box
                        DropdownMenu(
                            expanded = isContextMenuVisible,
                            onDismissRequest = { isContextMenuVisible = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Edit") },
                                onClick = {
                                    onActionSelected(WallpaperAlbumAction.EditAction)
                                    isContextMenuVisible = false // Close the menu after action
                                },
                                leadingIcon = { Icon(Icons.Default.Edit, contentDescription = "Rename") }
                            )
                            DropdownMenuItem(
                                text = { Text("Delete") },
                                onClick = {
                                    onActionSelected(WallpaperAlbumAction.DeleteAction)
                                    isContextMenuVisible = false
                                },
                                leadingIcon = { Icon(Icons.Default.Delete, contentDescription = "Delete") }
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed class WallpaperAlbumAction {

    data object SelectAction : WallpaperAlbumAction()

    data object OpenAction: WallpaperAlbumAction()

    data object EditAction : WallpaperAlbumAction()

    data object DeleteAction : WallpaperAlbumAction()


}