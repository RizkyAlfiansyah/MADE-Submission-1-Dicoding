package com.rizky.made.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizky.made.network.ApiRepository
import com.rizky.made.network.ApiServices
import com.rizky.made.model.TvShow
import com.rizky.made.model.TvShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowListViewModel : ViewModel() {
    private val listTV = MutableLiveData<ArrayList<TvShow>>()

    internal fun setTV() {
        val service = ApiRepository.createService(
            ApiServices::class.java
        )
        val call = service.DiscoverTV("en-US")
        call.enqueue(object: Callback<TvShowResponse> {
            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                val tvList = response.body() as TvShowResponse
                listTV.postValue(tvList.tvLists as ArrayList<TvShow>)
            }
        })
    }

    internal fun getTV(): LiveData<ArrayList<TvShow>> {
        return listTV
    }
}