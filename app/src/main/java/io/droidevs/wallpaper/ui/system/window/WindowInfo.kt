package io.droidevs.wallpaper.ui.system.window


import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.droidevs.wallpaper.ui.layouts.AppLayoutMode

@Stable
data class WindowInfo(
    @Stable
    val screenWidthInfo: WindowType = WindowType.Compact, // Represents the type of screen width.
    @Stable
    val screenHeightInfo: WindowType = WindowType.Compact, // Represents the type of screen height.
    @Stable
    val screenWidth: Dp = 0.dp, // Actual screen width in Dp.
    @Stable
    val screenHeight: Dp = 0.dp, // Actual screen height in Dp.

    val layout : AppLayoutInfo = AppLayoutInfo()
)

// Sealed class representing the type of window size.
sealed class WindowType {
    object Compact : WindowType() // Compact screen (small dimensions).
    object Medium : WindowType() // Medium-sized screen.
    object Expanded : WindowType() // Large screen.
}

// Abstract class to manage window state information.
// Provides composable and non-composable methods to update window info.
abstract class WindowInfoController {
    // Represents the current state of the window.
    abstract val state: State<WindowInfo>

    // Composable function to update the state based on a transformation function.
    @Composable
    abstract fun update(fn: @Composable (prevState: WindowInfo) -> WindowInfo)

    // Updates the state directly with a new state.
    abstract fun update(newState: WindowInfo)

    // Updates the screen width and height dynamically.
    abstract fun updateDimensions(screenWidth: Dp, screenHeight: Dp)

    // Updates the screen width and height types (e.g., Compact, Medium, Expanded).
    abstract fun updateTypes(widthType: WindowType, heightType: WindowType)

    // Resets the window info to default values.
    abstract fun reset()
}

// Default implementation of WindowInfoController.
// Provides a reactive and customizable controller for window info.
class DefaultWindowInfoController(
    initialState: WindowInfo = WindowInfo() // Default initial state.
) : WindowInfoController() {

    // Internal state using MutableState for Compose reactivity.
    private var _state: MutableState<WindowInfo> = mutableStateOf(initialState)
    override val state: State<WindowInfo> = _state

    // Composable function to update the state with a transformation function.
    @Composable
    override fun update(fn: @Composable (prevState: WindowInfo) -> WindowInfo) {
        val newState = fn(state.value)
        _state.value = newState
    }

    // Updates the state directly with a new WindowInfo instance.
    override fun update(newState: WindowInfo) {
        _state.value = newState
    }

    // Updates the screen width and height dynamically.
    override fun updateDimensions(screenWidth: Dp, screenHeight: Dp) {
        update(_state.value.copy(screenWidth = screenWidth, screenHeight = screenHeight))
    }

    // Updates the screen width and height types (e.g., Compact, Medium, Expanded).
    override fun updateTypes(widthType: WindowType, heightType: WindowType) {
        update(_state.value.copy(screenWidthInfo = widthType, screenHeightInfo = heightType))
    }

    // Resets the window info to its default state.
    override fun reset() {
        _state.value = WindowInfo() // Reset to default values.
    }
}

// Provides a CompositionLocal for accessing a WindowInfoController.
// Defaults to a new instance of DefaultWindowInfoController with expanded screen settings.
val LocalWindow: ProvidableCompositionLocal<WindowInfoController> =
    staticCompositionLocalOf {
        DefaultWindowInfoController(
            WindowInfo(
                screenWidthInfo = WindowType.Expanded,
                screenHeightInfo = WindowType.Expanded,
                screenWidth = 1080.dp,
                screenHeight = 2160.dp
            )
        )
    }
