package com.rizky.made.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizky.made.model.MovieResponse
import com.rizky.made.helper.ViewModelHelpers
import com.rizky.made.database.MovieDB
import com.rizky.made.network.ApiRepository
import com.rizky.made.network.ApiServices
import com.rizky.made.model.Movie
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListViewModel : ViewModel() {
    private val listMovies = MutableLiveData<ArrayList<Movie>>()
    private var viewModelHelpers: ViewModelHelpers? = null

    internal fun setViewModelHelpers(helper: ViewModelHelpers) {
        viewModelHelpers = helper
    }

    internal fun setMovies() {
        val service = ApiRepository.createService(ApiServices::class.java)
        val call = service.DiscoverMovie("en-US")
        call.enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                viewModelHelpers?.onFailure(t.message.toString())
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieList = response.body() as MovieResponse
                listMovies.postValue(movieList.movieLists as ArrayList<Movie>)
                viewModelHelpers?.onSuccess()
            }
        })
    }

    internal fun setFavoriteMovies(movieDB: MovieDB) {
        GlobalScope.launch {
            val lists = movieDB.getAllMovies() as ArrayList<Movie>
            listMovies.postValue(lists)
        }
    }

    internal fun getMovies(): LiveData<ArrayList<Movie>> {
        return listMovies
    }
}