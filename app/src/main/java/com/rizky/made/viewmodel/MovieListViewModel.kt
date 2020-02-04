package com.rizky.made.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizky.made.network.ApiRepository
import com.rizky.made.network.ApiServices
import com.rizky.made.model.Movie
import com.rizky.made.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListViewModel : ViewModel() {
    private val listMovies = MutableLiveData<ArrayList<Movie>>()

    internal fun setMovies() {
        val service = ApiRepository.createService(
            ApiServices::class.java
        )
        val call = service.DiscoverMovie("en-US")
        call.enqueue(object: Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieList = response.body() as MovieResponse
                listMovies.postValue(movieList.movieLists as ArrayList<Movie>)
            }
        })
    }

    internal fun getMovies(): LiveData<ArrayList<Movie>> {
        return listMovies
    }
}