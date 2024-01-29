package ru.testapp.cft_test.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.testapp.cft_test.R

fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.baseline_crop_original_24)
        .transition(DrawableTransitionOptions.withCrossFade())
        .circleCrop()
        .error(R.drawable.baseline_error_outline_24)
        .into(this)

}