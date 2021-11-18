package com.example.pixabayclean.data.network.entity


import com.google.gson.annotations.SerializedName

data class ImageSearchResponse(
    @SerializedName("hits")
    val networkImages: List<NetworkImage>?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("totalHits")
    val totalHits: Int?
)