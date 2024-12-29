package io.droidevs.wallpaper.ui.system.window

import android.graphics.Rect
import androidx.window.layout.FoldingFeature


/**
 * A suspend function that retrieves information about the foldable device's layout and updates the UI.
 *
 * @param activity The ComponentActivity instance used to track window layout information.
 */
suspend fun getFoldableInfo(foldingFeature: FoldingFeature) : FoldableInfo{
    // Create a FoldableInfo object if a FoldingFeature is present.
    return foldingFeature.let {
        FoldableInfo(
            hingeType = foldingFeature.occlusionType,
            orientation = foldingFeature.orientation,
            openedState = foldingFeature.state,
            showSeparateScreens = foldingFeature.isSeparating,
            bounds = foldingFeature.bounds
        )
    }
}

/**
 * Data class representing foldable device information.
 *
 * This class encapsulates details about the foldable device's hinge type, orientation, opened state,
 * whether it separates screens, and its bounding rectangle.
 *
 * @property hingeType The occlusion type of the folding feature (e.g., NONE, FULL, PARTIAL).
 * @property orientation The orientation of the folding feature (e.g., HORIZONTAL, VERTICAL).
 * @property openedState The state of the folding feature (e.g., FLAT, HALF_OPENED, FLIPPED).
 * @property showSeparateScreens Boolean indicating if the folding feature separates the screen into distinct areas.
 * @property bounds The bounding rectangle of the folding feature.
 */
class FoldableInfo(
    val hingeType: FoldingFeature.OcclusionType = FoldingFeature.OcclusionType.NONE, // Default occlusion type
    val orientation: FoldingFeature.Orientation = FoldingFeature.Orientation.HORIZONTAL, // Default orientation
    val openedState: FoldingFeature.State = FoldingFeature.State.FLAT, // Default folding state
    val showSeparateScreens: Boolean = false, // Default to no screen separation
    val bounds: Rect = Rect() // Default empty rectangle
) {

    /**
     * Determines if the foldable device is in a tabletop posture.
     *
     * @return True if the device is half-opened and horizontally oriented; false otherwise.
     */
    fun isTableTopPosture(): Boolean {
        return this.openedState == FoldingFeature.State.HALF_OPENED &&
                this.orientation == FoldingFeature.Orientation.HORIZONTAL
    }

    /**
     * Determines if the foldable device is in a book posture.
     *
     * @return True if the device is half-opened and vertically oriented; false otherwise.
     */
    fun isBookPosture(): Boolean {
        return this.openedState == FoldingFeature.State.HALF_OPENED &&
                this.orientation == FoldingFeature.Orientation.VERTICAL
    }
}
