package io.droidevs.wallpaper.ui.layouts


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.droidevs.wallpaper.ui.system.window.AppLayoutInfo


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoubleLayoutWithScaffold(
    appLayoutInfo: AppLayoutInfo,
    leftContent: @Composable () -> Unit,
    rightContent: @Composable () -> Unit,
    topAppBar: @Composable () -> Unit,
    isStandalone: Boolean = true // Determines whether to apply external padding
) {
    val sidePadding = when (appLayoutInfo.appLayoutMode) {
        AppLayoutMode.DOUBLE_BIG -> 52.dp
        AppLayoutMode.FOLDED_SPLIT_BOOK -> 16.dp
        else -> 16.dp
    }

    Scaffold(
        topBar = { topAppBar() }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .then(if (isStandalone) Modifier.padding(paddingValues) else Modifier) // Apply external padding if standalone
        ) {
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(end = sidePadding) // Keep internal padding
            ) {
                leftContent()
            }

            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
                    .padding(start = sidePadding) // Keep internal padding
            ) {
                rightContent()
            }
        }
    }
}
