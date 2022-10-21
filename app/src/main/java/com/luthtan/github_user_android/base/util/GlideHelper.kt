package com.luthtan.github_user_android.base.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object GlideHelper {
    fun showThumbnail(url: String, imageView: ImageView) {
        val options = RequestOptions().centerCrop()

        val requestBuilder = Glide.with(imageView.context)
            .load(url)

        requestBuilder
            .apply(options)
            .into(imageView)
    }
}