package com.example.pixabayclean.data.network.api_service

import com.example.pixabayclean.data.network.entity.ImageSearchResponse
import com.example.pixabayclean.utils.API_KEY
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApiService {
    @GET("/api/")
    fun searchImages(
        @Query("q") searchText: String = "fruits",
        @Query("key") key: String = API_KEY,
        @Query("page") page: Int? = 1,
    ): Single<ImageSearchResponse>
}