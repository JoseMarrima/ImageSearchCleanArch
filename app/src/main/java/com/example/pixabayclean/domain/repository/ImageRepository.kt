package com.example.pixabayclean.domain.repository

import androidx.paging.PagingData
import com.example.pixabayclean.domain.model.Image
import io.reactivex.Flowable

interface ImageRepository {
    fun searchImages(searchText: String): Flowable<PagingData<Image>>
}