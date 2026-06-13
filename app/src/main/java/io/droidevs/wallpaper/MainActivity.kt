package io.droidevs.wallpaper

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import dagger.hilt.android.AndroidEntryPoint
import io.droidevs.wallpaper.ui.utils.ObserveAsEventsCompose
import io.droidevs.wallpaper.ui.snackbar.SnackBarController
import io.droidevs.wallpaper.ui.theme.WallPaperTheme
import io.droidevs.wallpaper.ui.window.LocalWindow
import io.droidevs.wallpaper.ui.window.WindowInfo
import io.droidevs.wallpaper.ui.window.calculateWindowSize
import io.droidevs.wallpaper.ui.window.getFoldableInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var windowInfoFlow = mutableStateOf(WindowInfo())

    var windowInfoTracker : WindowInfoTracker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val configuration = applicationContext.resources.configuration
        val foldingFeatureState = mutableStateOf<FoldingFeature?>(null)

        // Fix leak for Android Q
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (isTaskRoot) {
                            finishAfterTransition()
                        }
                    }
                },
            )
        }

        enableEdgeToEdge()
        windowInfoTracker = WindowInfoTracker.getOrCreate(this)

        // Collect folding changes
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                windowInfoTracker!!.windowLayoutInfo(this@MainActivity)
                    .collect { layoutInfo ->
                        val foldingFeature = layoutInfo.displayFeatures.firstOrNull() as FoldingFeature
                        windowInfoFlow.value = windowInfoFlow.value.copy(foldableInfo = getFoldableInfo(foldingFeature))
                    }
            }
        }

        setContent {
            CompositionLocalProvider(LocalWindow provides windowInfoFlow.value) {
                WallPaperTheme {
                    val snackbarHostState = remember {
                        SnackbarHostState()
                    }
                    val scope = rememberCoroutineScope()
                    ObserveAsEventsCompose(
                        flow = SnackBarController.events,
                        snackbarHostState
                    ) { event ->
                        val message = stringResource(event.message)
                        LaunchedEffect(
                            true
                        ) {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            val result = snackbarHostState.showSnackbar(
                                message = message,
                                actionLabel = event.action?.name,
                                duration = SnackbarDuration.Long
                            )
                            if (result == SnackbarResult.ActionPerformed){
                                event.action?.onAction?.invoke()
                            }
                        }
                    }
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        snackbarHost = {
                            SnackbarHost(
                                hostState = snackbarHostState
                            )
                        }
                    ) { paddingValues ->
                        Content(paddingValues)
                    }
                }
            }
        }

    }

    private fun updateWindowSize() {
        windowInfoFlow.value = windowInfoFlow.value.copy(windowSize = calculateWindowSize(resources.displayMetrics.density,this))
    }

    @Composable
    private fun Content(
        paddingValues: PaddingValues
    ) {

    }
}
