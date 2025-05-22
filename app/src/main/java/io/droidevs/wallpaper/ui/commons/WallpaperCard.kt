package io.droidevs.wallpaper.ui.commons


import android.util.Log
import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import io.droidevs.wallpaper.R
import io.droidevs.wallpaper.domain.Album
import io.droidevs.wallpaper.domain.LocalWallpaper
import io.droidevs.wallpaper.ui.dialogs.menus.WallpaperMenu
import io.droidevs.wallpaper.ui.model.LightDarkType
import io.droidevs.wallpaper.ui.theme.WallPaperTheme
import kotlin.math.roundToInt


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WallpaperCard(
    modifier: Modifier = Modifier,
    albums: List<Album>,
    wallpaper: LocalWallpaper,
    blur: Boolean = false,
    fixedHeight: Boolean = false,
    roundedCorners: Boolean = true,
    isSelected: Boolean = false,
    isSelectionMode: Boolean,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit,
    onSelect : (Boolean) -> Unit,
    onDelete : () -> Unit,
    onMove: (album: Album) -> Unit,
    onMoveToNew: (albumName: String) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        WallpaperMenu(
            { showDialog = it },
            wallpaper = wallpaper,
            albums = albums,
            onDelete = onDelete,
            onMove = onMove,
            onMoveToNew = onMoveToNew
        )
    }
    val context = LocalContext.current
    val request = remember(
        context,
        wallpaper,
    ) {
        ImageRequest.Builder(context)
            .data(wallpaper.filePath)
    }

    val transition = updateTransition(isSelected, label = "selection state")
    val selectionColor by transition.animateColor(label = "selection color") {
        if (it) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else Color.Transparent
    }
    val selectionCircleColor by transition.animateColor(label = "circle color") {
        if (it) MaterialTheme.colorScheme.onPrimary else Color.Transparent
    }
    val checkImageColor by transition.animateColor(label = "check image color") {
        if (it) MaterialTheme.colorScheme.primary else Color.Transparent
    }
    val checkImageAlpha by transition.animateFloat(label = "check image alpha") {
        if (it) 1f else 0f
    }
    val checkImage = remember(context) {
        ResourcesCompat.getDrawable(
            context.resources,
            R.drawable.check,
            null,
        )?.toBitmap()?.asImageBitmap()
    }

    var displayWidth by remember { mutableIntStateOf(0) }
    var displayHeight by remember { mutableIntStateOf(0) }

    displayWidth = LocalView.current.width
    displayHeight = LocalView.current.height

    val aspectRatio by remember {
        mutableFloatStateOf(
            if (isOriginalAspectRatio()) {
                wallpaper.width?.toFloat()?.div(wallpaper.height!!) ?: (16f / 9f)
            } else {
                if (!(displayWidth.toFloat() / displayHeight.toFloat()).isNaN()) {
                    displayWidth.toFloat() / displayHeight.toFloat()
                } else {
                    16f / 9f
                }
            }
        )
    }

    var loaded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .let {
                if (fixedHeight) {
                    it.height(200.dp)
                } else {
                    it
                        .aspectRatio(aspectRatio)
                        .heightIn(min = 60.dp)
                }
            }
            .clip(if (roundedCorners) CardDefaults.shape else RectangleShape)
            .combinedClickable(
                onClick = onClick,
                onLongClick = {
                    showDialog = true
                }
            )
            .testTag("wallpaper"),
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 24.dp,
            ),
            modifier = Modifier
                .aspectRatio(aspectRatio)
                .padding(
                    start = 8.dp,
                    bottom = 35.dp,
                    end = 8.dp,
                    top = 8.dp
                )
                .shadow(
                    24.dp,
                    shape = RoundedCornerShape(16.dp),
                    clip = false,
                    spotColor = Color(wallpaper.prominentColor),
                    ambientColor = Color(wallpaper.prominentColor)
                ),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                AsyncImage(
                    modifier = Modifier
                        .blur(if (blur) 16.dp else 0.dp)
                        .clip(RectangleShape)
                        .fillMaxHeight()
                        .drawWithContent {
                            drawContent()
                            drawRect(selectionColor)
                            if (!isSelected) {
                                val radius = minOf(size.minDimension / 2f, 20.dp.toPx())
                                drawCircle(
                                    color = selectionCircleColor,
                                    radius = radius,
                                )
                                if (checkImage == null) return@drawWithContent
                                val imageSize = (radius * 1.5).roundToInt()
                                drawImage(
                                    image = checkImage,
                                    dstOffset = IntOffset(
                                        x = (size.width / 2 - imageSize / 2).roundToInt(),
                                        y = (size.height / 2 - imageSize / 2).roundToInt(),
                                    ),
                                    dstSize = IntSize(imageSize, imageSize),
                                    colorFilter = ColorFilter.tint(checkImageColor),
                                    alpha = checkImageAlpha,
                                )
                            } else {
                                drawRect(Color.Black.copy(alpha = 0.5f))
                            }
                        },
                    model = request,
                    placeholder = ColorPainter(
                        Color.White
                    ),
                    fallback = ColorPainter(
                        Color.White
                    ),
                    error = ColorPainter(
                        Color.White
                    ),
                    contentDescription = stringResource(R.string.wallpaper_content_description),
                    contentScale = ContentScale.Crop,
                    onError = {
                        Log.e(
                            "TAG",
                            "Error loading: ${wallpaper.id}",
                            it.result.throwable,
                        )
                    },
                    onSuccess = { loaded = true },
                )
            }

            WallpaperDimensionsText(wallpaper, displayWidth, displayHeight, isSelected)
        }

        // Favorite Button
        IconButton(
            onClick = onFavoriteClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(36.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                contentDescription = "Favorite",
                tint = if (isFavorite) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }
        ReportDrawnWhen { loaded }
    }
}

fun isOriginalAspectRatio() : Boolean {
    return TODO()
}


private data class Props(
    val wallpaper: LocalWallpaper = LocalWallpaper(),
    val albums: List<Album> = emptyList(),
    val blur: Boolean = false,
    val fixedHeight: Boolean = false,
    val roundedCorners: Boolean = true,
    val isSelected: Boolean = false,
    val isFavorite: Boolean = false,
    val isViewed: Boolean = false,
    val lightDarkTypeFlags: Int = LightDarkType.UNSPECIFIED,
)

private class PreviewProps : CollectionPreviewParameterProvider<Props>(
    listOf(
        Props(),
        Props(fixedHeight = true),
        Props(roundedCorners = false),
        Props(isSelected = true),
    ),
)

@Preview
@Composable
private fun PreviewWallpaperCard(
    @PreviewParameter(PreviewProps::class) props: Props,
) {
    val (
        wallpaper,
        albums,
        blur,
        fixedHeight,
        roundedCorners,
        isSelected
    ) = props
    WallPaperTheme {
        WallpaperCard(
            modifier = Modifier.width(200.dp),
            wallpaper = wallpaper,
            albums = albums,
            blur = blur,
            fixedHeight = fixedHeight,
            roundedCorners = roundedCorners,
            isSelected = isSelected,
            isSelectionMode = false,
            onClick = {},
            onSelect = {},
            onDelete = {},
            onMove = { Log.i("TAG", "onMove") },
            onMoveToNew = { Log.i("TAG", "onMoveToNew") },
            onFavoriteClick = {},
            isFavorite = false,
        )
    }
}