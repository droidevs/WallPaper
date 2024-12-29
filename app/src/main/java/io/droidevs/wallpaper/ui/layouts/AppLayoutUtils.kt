package io.droidevs.wallpaper.ui.layouts

import android.util.Log
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.window.layout.FoldingFeature
import io.droidevs.wallpaper.ui.system.window.AppLayoutInfo
import io.droidevs.wallpaper.ui.system.window.FoldableInfo
import io.droidevs.wallpaper.ui.system.window.WindowSizeInfo
import io.droidevs.wallpaper.ui.system.window.WindowType

enum class AppLayoutMode {
    PHONE_LANDSCAPE,
    PHONE_SPLIT_SQUARE,
    PHONE_PORTRAIT,
    NARROW_TABLET,
    DOUBLE_MEDIUM,
    DOUBLE_BIG,
    FOLDED_SPLIT_BOOK,
    FOLDED_SPLIT_TABLETOP;

    /**
     * Show the nav drawer when a bottom nav would be too
     * stretched and a side rail would cut into a double layout
     * too much. So this shows for a foldable with a double layout,
     * a very small window/phone size - split screen, or a medium
     * sized width screen.
     */
    fun showNavDrawer(startDestination: String): Boolean =
        (startDestination != null // TODO
                && (this == DOUBLE_MEDIUM || this == PHONE_SPLIT_SQUARE ||
                this == FOLDED_SPLIT_BOOK))

    /**
     * Show the nav rail mostly when we're in landscape mode, and
     * there's plenty of width to take up. Narrow tablet is only one
     * screen, so this is the exception.
     */
    fun showNavRail(): Boolean =
        (this == PHONE_LANDSCAPE || this == DOUBLE_BIG ||
                this == FOLDED_SPLIT_TABLETOP || this == NARROW_TABLET)

    /**
     * Bottom nav is perfect for a typical phone size, in portrait mode.
     */
    fun showBottomNav(): Boolean =
        (this == PHONE_PORTRAIT)

    /**
     * Only these layouts have a double | split screen.
     */
    fun isSplitScreen(): Boolean =
        (this == DOUBLE_MEDIUM || this == DOUBLE_BIG)

    fun isSplitFoldable(): Boolean =
        (this == FOLDED_SPLIT_TABLETOP || this == FOLDED_SPLIT_BOOK)

}


fun getWindowLayoutType(
    windowSizeInfo: WindowSizeInfo,
    foldableInfo: FoldableInfo?
): AppLayoutInfo = with(windowSizeInfo) {
    Log.d("debug", "windowWH: $widthType;$heightType, ${size.width};${size.height}")
    //Log.d("debug", "screenInfo: ${windowInfo.rotation};${foldableInfo?.bounds}")

    // First, I check to see if it's a foldable, with dual screen (isSeparating).
    if ((foldableInfo != null) && foldableInfo.showSeparateScreens) {
        getFoldableAppLayout(foldableInfo, size)
    } else {
        // Check for a typical phone size, landscape mode.
        if (heightType == WindowType.Compact)
            getLandscapeLayout(widthType, size)
        else {
            getPortraitLayout(widthType, size)
        }
    }
}


fun getFoldableAppLayout(foldableInfo: FoldableInfo, size: DpSize): AppLayoutInfo {
    return if (foldableInfo.orientation == FoldingFeature.Orientation.VERTICAL)
        AppLayoutInfo(
            AppLayoutMode.FOLDED_SPLIT_BOOK,
            size,
            foldableInfo
        )
    else
        AppLayoutInfo(
            AppLayoutMode.FOLDED_SPLIT_TABLETOP,
            size,
            foldableInfo
        )
}

fun getLandscapeLayout(windowWidth: WindowType, size: DpSize): AppLayoutInfo =
    if (windowWidth == WindowType.Compact) {
        // This is if it's very small; phone size & split screen.
        AppLayoutInfo(
            AppLayoutMode.PHONE_SPLIT_SQUARE,
            size
        )
    } else {
        AppLayoutInfo(AppLayoutMode.PHONE_LANDSCAPE, size)
    }

fun getPortraitLayout(windowWidth: WindowType, size: DpSize): AppLayoutInfo =
// At this point, I know it's not a landscape/rotated phone size.
    // So let's check the width.
    when (windowWidth) {
        WindowType.Compact -> AppLayoutInfo(AppLayoutMode.PHONE_PORTRAIT, size)
        WindowType.Medium -> {
            // some tablets measure 600.93896; just over 600;
            // let's give this some padding, and make a new cut-off.
            if (size.width <= 650.dp)
                AppLayoutInfo(
                    AppLayoutMode.NARROW_TABLET,
                    size
                )
            else
                AppLayoutInfo(
                    AppLayoutMode.DOUBLE_MEDIUM,
                    size
                )
        }
        else -> {
            // override the expanded threshold. 800 vs 1000+ is big diff.
            if (size.width < 950.dp)
                AppLayoutInfo(
                    AppLayoutMode.DOUBLE_MEDIUM,
                    size
                )
            else
                AppLayoutInfo(AppLayoutMode.DOUBLE_BIG, size)
        }
    }