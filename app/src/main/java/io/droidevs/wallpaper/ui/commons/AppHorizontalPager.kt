package io.droidevs.wallpaper.ui.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import io.droidevs.wallpaper.ui.PrimaryTabIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHorizontalPager(
    pageCount : Int,
    coroutineScope: CoroutineScope,
    tabItem: (Int) -> TabItem,
){

    val pagerState = rememberPagerState(pageCount = { pageCount })



    PrimaryTabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = {
            PrimaryTabIndicator(pagerState.currentPage)
        },
    ) {
        (1..pageCount).forEach { index ->
            var tab = tabItem(index)
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                },
                text = { Text(tab.title) }
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(
            state = pagerState,
            pageSize = PageSize.Fill,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Pager takes up all available vertical space
        ) { page ->
            tabItem(page).content()
        }
    }

    PagerIndicator(
        pagerState = pagerState,
        modifier = Modifier.padding(vertical = 24.dp)
    )

}


public class TabItem(val title: String, val icon: ImageVector, val content: @Composable () -> Unit)

