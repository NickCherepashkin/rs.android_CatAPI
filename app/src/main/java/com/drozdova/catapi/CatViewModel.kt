package com.drozdova.catapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.drozdova.catapi.data.Cat

class CatViewModel : ViewModel() {
    private val catPageSource: CatsPageSource = CatsPageSource(CatApiImpl)
    var cat = MutableLiveData<Cat>()

    val catsFlow = Pager (
        PagingConfig(
            pageSize = 10
        )
    ) {
        catPageSource
    }.flow.cachedIn(viewModelScope)
}