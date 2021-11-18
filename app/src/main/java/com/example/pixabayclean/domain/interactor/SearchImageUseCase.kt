package com.example.pixabayclean.domain.interactor

import com.example.pixabayclean.domain.repository.ImageRepository
import javax.inject.Inject

class SearchImageUseCase @Inject constructor(private val imageRepository: ImageRepository) {
    operator fun invoke(query: String) = imageRepository.searchImages(query)
}