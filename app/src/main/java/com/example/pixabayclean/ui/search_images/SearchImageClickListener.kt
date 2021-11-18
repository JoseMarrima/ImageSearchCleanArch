package com.example.pixabayclean.ui.search_images

import com.example.pixabayclean.domain.model.Image

class SearchImageClickListener(val clickListener: (item: Image) -> Unit) {
    fun onClick(item: Image) = clickListener(item)
}