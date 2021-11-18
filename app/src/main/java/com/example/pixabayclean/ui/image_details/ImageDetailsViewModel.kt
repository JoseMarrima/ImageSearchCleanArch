package com.example.pixabayclean.ui.image_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.pixabayclean.domain.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageDetailsViewModel @Inject constructor(state: SavedStateHandle) : ViewModel() {
    // Keep the key as a constant
    companion object {
        private const val IMAGE = "image"
    }

    private val _image: MutableLiveData<Image> = state.getLiveData(IMAGE)
    val image: LiveData<Image> = _image

    val allTags = image.value?.tags?.trim()?.split(",") ?: emptyList()
}