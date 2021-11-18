package com.example.pixabayclean.data.repository

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.example.pixabayclean.data.mapper.Mapper
import com.example.pixabayclean.data.mapper.NullableInputListMapperImpl
import com.example.pixabayclean.data.network.api_service.ImageApiService
import com.example.pixabayclean.data.network.entity.NetworkImage
import com.example.pixabayclean.domain.model.Image
import com.example.pixabayclean.utils.FRUITS
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ImagePagingSource @Inject constructor(private val remoteImageMapper: Mapper<NetworkImage, Image>,
                        private val imageApiService: ImageApiService) : RxPagingSource<Int, Image>() {

    private lateinit var query: String

    fun search(searchText: String) {
        query = searchText
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        val anchorPosition: Int = state.anchorPosition ?: return null
        val (_, prevKey, nextKey) = state.closestPageToPosition(anchorPosition) ?: return null

        if (prevKey != null) {
            return prevKey + 1
        }

        return if (nextKey != null) {
            nextKey - 1
        } else null

    }

    private fun toLoadResult(data: List<Image>, position: Int): LoadResult<Int, Image> {
        return LoadResult.Page(
            data = data,
            prevKey = if (position == 1) null else position - 1,
            nextKey = position + 1
        )
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Image>> {
        val nextPageNumber: Int = params.key ?: 1

        if (query.isEmpty()) query = FRUITS
        return imageApiService.searchImages(query, page = nextPageNumber)
            .subscribeOn(Schedulers.io())
            .map {
                val images = NullableInputListMapperImpl(remoteImageMapper).map(it.networkImages)
                toLoadResult(images, nextPageNumber)
            }
            .onErrorReturn { LoadResult.Error(it) }
    }
}