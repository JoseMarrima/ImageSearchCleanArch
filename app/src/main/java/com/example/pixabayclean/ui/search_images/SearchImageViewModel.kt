package com.example.pixabayclean.ui.search_images

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.example.pixabayclean.domain.interactor.SearchImageUseCase
import com.example.pixabayclean.domain.model.Image
import com.example.pixabayclean.utils.FRUITS
import com.example.pixabayclean.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class SearchImageViewModel @Inject constructor(
    private val searchImageUseCase: SearchImageUseCase
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _response: MutableLiveData<Resource<PagingData<Image>>> = MutableLiveData()
    val response: LiveData<Resource<PagingData<Image>>> = _response

    init {
        searchImages(query = FRUITS)
    }

    fun searchImages(query: String) {
        disposables.add(searchImageUseCase.invoke(query)
            .cachedIn(viewModelScope)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                _response.value = Resource.loading(null)
            }
            .doOnSubscribe {
                _response.value = Resource.loading(null)
            }
            .subscribe {
                _response.value = Resource.success(it)
            })
    }
}