package io.droidevs.wallpaper.ui.commons

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun TabRowIndicator(
    tabPositions: List<TabPosition>,
    tabIndex: Int
){
    val transition = updateTransition(tabIndex)
    val indicatorStart = transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 50f)
            } else {
                spring(dampingRatio = 1f, stiffness = 1000f)
            }
        },
        label = ""
    ) {
        tabPositions[it].left
    }

    val indicatorEnd = transition.animateDp(
        transitionSpec = {
            if (initialState < targetState){
                spring(dampingRatio = 1f, stiffness = 1000f)
            } else {
                spring(dampingRatio = 1f, stiffness = 50f)
            }
        },
        label = ""
    ) {
        tabPositions[it].right
    }

    Box(
        modifier = Modifier
            .offset(x = indicatorStart.value)
            .wrapContentSize(align = Alignment.BottomStart)
            .width(indicatorEnd.value - indicatorStart.value)
            .padding(2.dp)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onBackground, RoundedCornerShape(50))
            .border(BorderStroke(2.dp, MaterialTheme.colorScheme.inverseSurface), RoundedCornerShape(50))
            .zIndex(1f)
    )
}