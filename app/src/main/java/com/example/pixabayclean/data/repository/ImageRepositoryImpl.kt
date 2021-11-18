package com.example.pixabayclean.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.pixabayclean.domain.model.Image
import com.example.pixabayclean.domain.repository.ImageRepository
import io.reactivex.Flowable
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
private val imagePagingSource: ImagePagingSource) : ImageRepository {
    override fun searchImages(searchText: String): Flowable<PagingData<Image>> {

        imagePagingSource.search(searchText)
        return  Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { imagePagingSource }
        ).flowable
    }
    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}