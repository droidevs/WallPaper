package io.droidevs.wallpaper.ui.bottomsheet.autoWallpaper

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.droidevs.wallpaper.R
import io.droidevs.wallpaper.domain.Album
import io.droidevs.wallpaper.ui.bottomsheet.AdaptiveBottomSheet
import io.droidevs.wallpaper.ui.bottomsheet.rememberAdaptiveBottomSheetState
import io.droidevs.wallpaper.util.isValidUri
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumChooserBottomSheet(
    albums: List<Album>,
    onAlbumSelected: (Album) -> Unit
) {
    val sheetState = rememberAdaptiveBottomSheetState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var selectedAlbumId by remember { mutableStateOf<Long?>(null) }

    AdaptiveBottomSheet(
        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant),
        sheetState = sheetState,
        onDismissRequest = {
            scope.launch { sheetState.hide() }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Choose an Album",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(albums.size) { albumIndex ->
                    val album = albums[albumIndex]
                    val isSelected = selectedAlbumId == album.id

                    ListItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                selectedAlbumId = album.id
                                onAlbumSelected(album)
                                scope.launch { sheetState.hide() }
                            }
                            .animateContentSize(),
                        leadingContent = {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Brush.linearGradient(listOf(Color.Gray, Color.Black.copy(0.3f))))
                            ) {
                                if (isValidUri(context, album.coverImageUrl)) {
                                    AsyncImage(
                                        model = album.coverImageUrl,
                                        contentDescription = "Album Cover",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Icon(
                                        //todo : change the holder icon to valid one
                                        imageVector = ImageVector.vectorResource(R.drawable.check),
                                        contentDescription = "Placeholder",
                                        modifier = Modifier
                                            .size(30.dp)
                                            .align(Alignment.Center),
                                        tint = Color.White.copy(0.8f)
                                    )
                                }
                            }
                        },
                        headlineContent = {
                            Text(
                                text = album.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        },
                        supportingContent = {
                            Text(
                                text = context.resources.getQuantityString(R.plurals.wallpaper_count, album.total, album.total),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        trailingContent = {
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Selected",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}
