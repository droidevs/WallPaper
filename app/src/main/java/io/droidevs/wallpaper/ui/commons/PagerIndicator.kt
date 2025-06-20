package io.droidevs.wallpaper.ui.commons

import androidx.compose.foundation.pager.HorizontalPager

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    inactiveColor: Color = activeColor.copy(alpha = 0.3f),
    activeWidth: Dp = 24.dp,
    inactiveWidth: Dp = 8.dp,
    indicatorHeight: Dp = 8.dp,
    spacing: Dp = 8.dp,
) {
    Row(
        modifier = modifier
            .height(indicatorHeight)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val isSelected = pagerState.currentPage == iteration

            // Animate color and width changes
            val color: Color by animateColorAsState(
                targetValue = if (isSelected) activeColor else inactiveColor,
                animationSpec = tween(durationMillis = 300),
                label = "indicator_color"
            )
            val width: Dp by animateDpAsState(
                targetValue = if (isSelected) activeWidth else inactiveWidth,
                animationSpec = tween(durationMillis = 300),
                label = "indicator_width"
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = spacing / 2)
                    .clip(CircleShape)
                    .background(color)
                    .size(width = width, height = indicatorHeight)
            )
        }
    }
}