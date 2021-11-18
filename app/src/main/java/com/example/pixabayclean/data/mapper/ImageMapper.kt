package com.example.pixabayclean.data.mapper

import com.example.pixabayclean.data.network.entity.NetworkImage
import com.example.pixabayclean.domain.model.Image
import javax.inject.Inject

class RemoteImageMapper @Inject constructor() : Mapper<NetworkImage, Image> {
    override fun map(input: NetworkImage): Image {
        return Image(
            input.id,
            input.previewURL.orEmpty(),
            input.user.orEmpty(),
            input.tags.orEmpty(),
            input.largeImageURL.orEmpty(),
            input.likes ?: 0,
            input.downloads ?: 0,
            input.comments ?: 0
        )
    }
}