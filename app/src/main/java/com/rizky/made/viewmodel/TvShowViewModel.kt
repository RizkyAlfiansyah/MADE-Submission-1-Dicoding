package com.rizky.made.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizky.made.model.TvShow

class TvShowViewModel : ViewModel() {
    private val tv = MutableLiveData<TvShow>()

    internal fun setTV(item: TvShow) {
        tv.postValue(item)
    }

    internal fun getTV(): LiveData<TvShow> {
        return tv
    }
}