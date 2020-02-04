package com.rizky.made.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizky.made.helper.ViewModelHelpers
import com.rizky.made.network.ApiRepository
import com.rizky.made.network.ApiServices
import com.rizky.made.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {
    private val movie = MutableLiveData<Movie>()
    private var viewModelHelpers: ViewModelHelpers? = null

    internal fun setViewModelHelpers(helper: ViewModelHelpers) {
        viewModelHelpers = helper
    }

    internal fun setMovie(movieId: Int, language: String) {
        val service = ApiRepository.createService(ApiServices::class.java)
        val call = service.GetMovie(movieId, language)
        call.enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                viewModelHelpers?.onFailure(t.message.toString())
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                val item = response.body() as Movie
                movie.postValue(item)
                viewModelHelpers?.onSuccess()
            }

        })
    }

    internal fun getMovie(): LiveData<Movie> {
        return movie
    }
}