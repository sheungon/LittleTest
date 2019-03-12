package com.example.littletest.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object ImageViewBindingAdapter {

    @JvmStatic
    @BindingAdapter(
        value = [
            "android:src",
            "picasso"
        ], requireAll = false
    )
    fun setImageUrl(
        imageView: ImageView,
        url: String?,
        picasso: Picasso?
    ) {
        if (url == null) {
            imageView.setImageDrawable(null)
        } else {
            (picasso ?: Picasso.get()).load(url)
                .fit()
                .into(imageView)
        }
    }
}