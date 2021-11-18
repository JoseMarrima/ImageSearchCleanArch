package com.example.pixabayclean.ui.search_images

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pixabayclean.R

object BindingAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun bindImage(imgView: ImageView, imgUrl: String?) {
        imgUrl?.let {
            val imgUri = imgUrl.toUri()

            Glide.with(imgView.context)
                .load(imgUri)
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(R.drawable.ic_placeholder_image)
                .error(R.drawable.ic_broken_image)
                .into(imgView)
        }
    }
}