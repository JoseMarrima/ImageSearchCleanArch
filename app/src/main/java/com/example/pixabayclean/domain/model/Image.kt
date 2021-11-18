package com.example.pixabayclean.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val id: Int,
    val thumbnailUrl: String,
    val userName: String,
    val tags: String,
    val largeImageUrl: String,
    val numberOfLikes: Int,
    val numberOfDownload: Int,
    val numberOfComments: Int,
) : Parcelable