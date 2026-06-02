package io.droidevs.wallpaper.ui.commons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.droidevs.wallpaper.ui.model.albums.AlbumUi


@Composable
fun WallpaperAlbumCard(
    isSelectMode: Boolean,
    album: AlbumUi,
    onActionSelected: (WallpaperAlbumAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val isHovered by interactionSource.collectIsHoveredAsState()
    val elevation by animateDpAsState(
        if (isHovered) 8.dp else 4.dp,
        label = "cardElevation"
    )

    val borderColor by animateColorAsState(
        targetValue = if (album.isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
        label = "borderColor"
    )

    // The list of actions to show in the menu
    val actions = listOf(
        WallpaperAlbumAction.SelectAction,
        WallpaperAlbumAction.OpenAction,
        WallpaperAlbumAction.EditAction,
        WallpaperAlbumAction.DeleteAction
    )


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
            defaultElevation = elevation,
            pressedElevation = 2.dp
        ),
        // --- SELECTION BORDER ---
        border = BorderStroke(2.dp, borderColor)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .indication(interactionSource, indication = LocalIndication.current)
                .pointerInput(true){
                    detectTapGestures(
                        onTap = {
                            val action = if (isSelectMode) {
                                if (album.isSelected)
                                     WallpaperAlbumAction.DeselectAction
                                else
                                    WallpaperAlbumAction.SelectAction
                            } else {
                                WallpaperAlbumAction.OpenAction
                            }
                            onActionSelected(action)
                        },
                        onPress = {
                            var press = PressInteraction.Press(it)
                            interactionSource.emit(press)
                            tryAwaitRelease()
                            interactionSource.emit(PressInteraction.Release(press))
                        },
                        onLongPress = {
                            if (!isSelectMode)
                                onActionSelected(WallpaperAlbumAction.SelectAction)
                        }
                    )
                }.padding(12.dp)
        ) {
            // Main content
            Column {

                Box {
                    // Image Preview
                    AsyncImage(
                        model = album.coverImageUrl,
                        contentDescription = "Album preview",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    )

                    // --- ANIMATED CHECKBOX ---
                    // Appears only in select mode.
                    AnimatedVisibility(
                        visible = isSelectMode,
                        label = "",
                        modifier = Modifier.align(Alignment.TopEnd),
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        SelectCheckboxWithBackground(
                            isSelected = album.isSelected,
                            onToggle = {
                                if (album.isSelected)
                                    onActionSelected(WallpaperAlbumAction.DeselectAction)
                                else
                                    onActionSelected(WallpaperAlbumAction.SelectAction)
                            }
                        )
                    }
                }

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
                            color = MaterialTheme.colorScheme.onSurfaceVariant//color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }

                    Box {
                        var isContextMenuVisible by rememberSaveable {
                            mutableStateOf(false)
                        }
                        IconButton(
                            modifier = Modifier.align(Alignment.TopEnd),
                            onClick = {
                                isContextMenuVisible = true
                            },
                            enabled = !isSelectMode
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


    data object DeselectAction : WallpaperAlbumAction()

}