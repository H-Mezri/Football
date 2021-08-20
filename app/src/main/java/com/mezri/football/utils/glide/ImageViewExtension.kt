package com.mezri.football.utils.glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.mezri.football.R

fun ImageView.load(url: String, loadOnlyFromCache: Boolean = false, animateImage: Boolean = false, onLoadingFinished: () -> Unit) {

    val listener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingFinished()
            return false
        }
    }

    var requestOptions = RequestOptions
            .errorOf(R.drawable.ic_broken_image)
            .fallback(R.drawable.ic_broken_image)
            .dontTransform()
            .onlyRetrieveFromCache(loadOnlyFromCache)

    if (!animateImage) {
        requestOptions = requestOptions.dontAnimate()
    }

    Glide.with(this)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .apply(requestOptions)
        .listener(listener)
        .into(this)
}