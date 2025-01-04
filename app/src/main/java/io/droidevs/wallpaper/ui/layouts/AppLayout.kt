package io.droidevs.wallpaper.ui.layouts

import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.droidevs.wallpaper.ui.commons.NavigationDrawer
import io.droidevs.wallpaper.ui.system.System
import io.droidevs.wallpaper.ui.system.window


@Composable
fun AppLayout(){
    val layoutInfo  = System.window.state.value.layout
    val layoutMode = layoutInfo.appLayoutMode

    with(layoutMode){
        when{
            isSplitFoldable() and isSplitScreen() -> {
                Log.d("debug", "using foldable layout...")
            }
            else -> {

            }
        }
    }
}

@Composable
fun useBottomNavBar(){
    Scaffold(
        bottomBar = {

        }
    ) {

    }
}

@Composable
fun useNavRail(){

}

@Composable
fun useNavigationDrawer(){
    val items = listOf(0)
    NavigationDrawer(
        modifier = Modifier,
        items = items,
        onItemClick = {
            with(it.id){
                when(it.id){

                }
            }
        }
    ) {

    }
}
