package io.droidevs.wallpaper.ui.dialogs.menus

import android.content.Intent

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import io.droidevs.wallpaper.R
import io.droidevs.wallpaper.domain.Album
import io.droidevs.wallpaper.domain.LocalWallpaper
import io.droidevs.wallpaper.ui.commons.AlbumListBottomSheet
import io.droidevs.wallpaper.ui.theme.AppTypography
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperMenu(
    setShowDialog: (Boolean) -> Unit,
    albums: List<Album>,
    wallpaper: LocalWallpaper,
    onDelete: () -> Unit,
    onMove: (album: Album) -> Unit,
    onMoveToNew: (albumName: String) -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var launchAlbumPicker by remember { mutableStateOf(false) }

    if (launchAlbumPicker) {
        AlbumListBottomSheet(
            onAlbumClick = { album ->
                onMove(album)
                setShowDialog(false)
            },
            onDismissRequest = { launchAlbumPicker = false },
            albums = albums,
            selectedAlbum = wallpaper.albumID.toLong(),
            onAlbumCreated = {
                onMoveToNew.invoke(it)
            }
        )
    }

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(16.dp))
                .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
                .animateContentSize()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = wallpaper.name ?: "",
                    style = AppTypography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                    thickness = 1.dp
                )

                //todo add favorite button

                ModernButton(
                    text = stringResource(R.string.send),
                    icon = Icons.Outlined.Share,
                    onClick = {
                        val uri = FileProvider.getUriForFile(
                            context,
                            context.packageName + ".provider",
                            wallpaper.filePath!!.toFile()
                        )
                        ShareCompat.IntentBuilder(context)
                            .setType("image/*")
                            .setChooserTitle("Share Wallpaper")
                            .setStream(uri)
                            .startChooser()
                    }
                )

                ModernButton(
                    text = stringResource(R.string.delete),
                    icon = Icons.Outlined.Delete,
                    onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            if (wallpaper.filePath!!.toFile().delete()) {
                                withContext(Dispatchers.Main) {
                                    onDelete()
                                    setShowDialog(false)
                                }
                            }
                        }
                    }
                )

                val editTitle = stringResource(R.string.edit)
                ModernButton(
                    text = editTitle,
                    icon = Icons.Outlined.Edit,
                    onClick = {
                        val uri = FileProvider.getUriForFile(
                            context,
                            context.packageName + ".provider",
                            wallpaper.filePath!!.toFile()
                        )
                        val intent = Intent(Intent.ACTION_EDIT)
                        intent.setDataAndType(uri, "image/*")
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        context.startActivity(Intent.createChooser(intent, editTitle))
                        setShowDialog(false)
                    }
                )

                ModernButton(
                    text = stringResource(R.string.move),
                    icon = Icons.AutoMirrored.Outlined.List,
                    onClick = {
                        launchAlbumPicker = true
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = { setShowDialog(false) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.close),
                        style = AppTypography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun ModernButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = AppTypography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

fun String.toFile(): File {
    return File(this)
}
