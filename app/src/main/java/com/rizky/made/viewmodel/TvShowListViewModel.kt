package com.rizky.made.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizky.made.database.TvDB
import com.rizky.made.helper.ViewModelHelpers
import com.rizky.made.model.TvShow
import com.rizky.made.model.TvShowResponse
import com.rizky.made.network.ApiRepository
import com.rizky.made.network.ApiServices
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowListViewModel : ViewModel() {
    private val listTV = MutableLiveData<ArrayList<TvShow>>()
    private var viewModelHelpers: ViewModelHelpers? = null

    internal fun setViewModelHelpers(helper: ViewModelHelpers) {
        viewModelHelpers = helper
    }

    internal fun setTV() {
        val service = ApiRepository.createService(ApiServices::class.java)
        val call = service.DiscoverTV("en-US")
        call.enqueue(object : Callback<TvShowResponse> {
            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                viewModelHelpers?.onFailure(t.message.toString())
            }

            override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                val tvList = response.body() as TvShowResponse
                listTV.postValue(tvList.tvLists as ArrayList<TvShow>)

                viewModelHelpers?.onSuccess()
            }
        })
    }

    internal fun setFavoriteTV(tvDB: TvDB) {
        GlobalScope.launch {
            val list = tvDB.getAllTV() as ArrayList<TvShow>
            listTV.postValue(list)
        }
    }

    internal fun getTV(): LiveData<ArrayList<TvShow>> {
        return listTV
    }

}