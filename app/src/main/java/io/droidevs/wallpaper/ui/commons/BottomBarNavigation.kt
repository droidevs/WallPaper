package io.droidevs.wallpaper.ui.commons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import io.droidevs.wallpaper.ui.model.BottomNavigationItem

@Composable
fun BottomNavigationBar(
    navigationItems : List<BottomNavigationItem>,
    selected : Int,
    onNavigateTo : (id : Int) -> Unit
    ) {
    NavigationBar(
        //containerColor = MaterialTheme.colorScheme.primaryContainer,
        //tonalElevation = 20.dp,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        navigationItems.forEach { item ->
            NavigationBarItem(
                label = { Text(text = item.title) },
                selected = item.id == selected,
                onClick = { onNavigateTo(item.id) },
                icon = {
                    Icon(
                        imageVector =
                        if (selected == item.id)
                            item.selectedIcon
                        else
                            item.unselectedIcon,

                        contentDescription = null,
                    )
                }
            )
        }
    }
}


@Composable
fun previewNavigationItems(): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            id = 0,
            title = "Home",
            selectedIcon = ImageVector.vectorResource(android.R.drawable.star_on),
            unselectedIcon = ImageVector.vectorResource(android.R.drawable.star_off),
            hasNews = true
        ),
        BottomNavigationItem(
            id = 1,
            title = "Search",
            selectedIcon = ImageVector.vectorResource(android.R.drawable.ic_menu_search),
            unselectedIcon = ImageVector.vectorResource(android.R.drawable.ic_menu_search),
            hasNews = false
        ),
        BottomNavigationItem(
            id = 2,
            title = "Profile",
            selectedIcon = ImageVector.vectorResource(android.R.drawable.ic_menu_manage),
            unselectedIcon = ImageVector.vectorResource(android.R.drawable.ic_menu_manage),
            hasNews = true,
            badgeCount = 3
        ),
    )
}

// Preview for BottomNavigationBar
@Preview(showBackground = true, name = "BottomNavigationBar Preview - Home Selected")
@Composable
fun BottomNavigationBarPreviewHome() {
    MaterialTheme {
        BottomNavigationBar(
            navigationItems = previewNavigationItems(),
            selected = 0,
            onNavigateTo = {}
        )
    }
}

@Preview(showBackground = true, name = "BottomNavigationBar Preview - Search Selected")
@Composable
fun BottomNavigationBarPreviewSearch() {
    MaterialTheme {
        BottomNavigationBar(
            navigationItems = previewNavigationItems(),
            selected = 1,
            onNavigateTo = {}
        )
    }
}

@Preview(showBackground = true, name = "BottomNavigationBar Preview - Profile Selected")
@Composable
fun BottomNavigationBarPreviewProfile() {
    MaterialTheme {
        BottomNavigationBar(
            navigationItems = previewNavigationItems(),
            selected = 2,
            onNavigateTo = {}
        )
    }
}
