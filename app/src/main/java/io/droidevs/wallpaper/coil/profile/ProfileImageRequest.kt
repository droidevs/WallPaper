package io.droidevs.wallpaper.coil.profile

import android.content.Context
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import io.droidevs.wallpaper.domain.Profile

class ProfileImageRequest {


    companion object {
        fun newImageRequest(context : Context, profile: Profile) {
            ImageRequest.Builder(context)
                .data(profile.image)
                .crossfade(true)
                .transformations(CircleCropTransformation())

        }
    }
}