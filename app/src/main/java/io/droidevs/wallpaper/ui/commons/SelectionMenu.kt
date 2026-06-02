package io.droidevs.wallpaper.ui.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.droidevs.wallpaper.R
import io.droidevs.wallpaper.ui.dialogs.settings.SureDialog



@Composable
fun SelectionMenu(
    modifier: Modifier = Modifier,
    count : Int = 0,
    navigationBarHeight: Dp = 0.dp,
    onDelete: () -> Unit,
    onClose: () -> Unit,
    onShare: () -> Unit
) {

    val context = LocalContext.current
    val iconSize = 56.dp
    var showDeleteSureDialog by remember { mutableStateOf(false) }
    var launchDirectoryPicker by remember { mutableStateOf(false) }

    if (showDeleteSureDialog) {
        SureDialog(title = stringResource(R.string.delete),
            text = stringResource(R.string.delete_selected_wallpapers_message, count),
            onConfirm = {
                // Delete the selected wallpapers
                showDeleteSureDialog = false
                onDelete.invoke()
            },
            onDismiss = {
                showDeleteSureDialog = false
            }
        )
    }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
        ),
        modifier = modifier
            .wrapContentHeight()
            .padding(16.dp)
            .padding(bottom = navigationBarHeight)
            .clip(RoundedCornerShape(16.dp))
            ,
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(48.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(
                onClick = {
                    showDeleteSureDialog = true
                },
                modifier = Modifier
                    .size(iconSize)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = null,
                )
            }
            IconButton(
                onClick = {
                    onShare.invoke()
                    /*
                    val files = selectedWallpapers.filter { it.isSelected }.map { it.filePath.toFile() }
                    val filesUri = files.map {
                        FileProvider.getUriForFile(
                            context,
                            context.packageName + ".provider",
                            it
                        )
                    }
                    val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
                    intent.type = "image/*"
                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(filesUri))
                    context.startActivity(Intent.createChooser(intent, "Share Wallpapers"))
                    */*/

                },
                modifier = Modifier
                    .size(iconSize)
                    .padding(end = 5.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Share,
                    contentDescription = null,
                )
            }
            VerticalDivider(
                modifier = Modifier
                    .width(1.dp)
                    .height(iconSize),
            )
            Text(
                text = count.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 24.dp, end = 8.dp)
                    .align(Alignment.CenterVertically)
            )
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .size(iconSize)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                )
            }
        }
    }
}