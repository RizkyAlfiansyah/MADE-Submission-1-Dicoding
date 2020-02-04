package com.rizky.madefinalsubmission.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizky.madefinalsubmission.model.MovieResponse
import com.rizky.madefinalsubmission.network.ApiRepository
import com.rizky.madefinalsubmission.network.ApiService
import com.rizky.madefinalsubmission.view.MainActivity
import com.rizky.madefinalsubmission.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MainViewModel : ViewModel() {
    private val movieList: MutableLiveData<ArrayList<Movie>> = MutableLiveData()
    private val apiService = ApiRepository.getClient()?.create(ApiService::class.java)
    fun setMovies(movieType: String?) {
        apiService?.getMovies(movieType)?.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(call: Call<MovieResponse?>?, response: Response<MovieResponse?>?) {
                try {
                    val movies = response?.body()?.results
                    if (movies != null) {
                        for (data in movies) {
                            data.movieType = movieType
                        }
                    }
                    movieList.postValue(movies as ArrayList<Movie>?)
                } catch (e: Exception) {
                    Log.d(MainActivity::class.java.simpleName, e.localizedMessage)
                }
            }

            override fun onFailure(call: Call<MovieResponse?>?, t: Throwable?) {
                if (t != null) {
                    Log.d(MainActivity::class.java.simpleName, t.localizedMessage)
                }
            }
        })
    }

    fun setFavMovie(movies: ArrayList<Movie>) {
        movieList.postValue(movies)
    }

    fun searchMovie(query: String?) {
        apiService?.searchMovies(query)?.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(call: Call<MovieResponse?>?, response: Response<MovieResponse?>?) {
                try {
                    val movies = response?.body()?.results
                    movieList.postValue(movies as ArrayList<Movie>)
                    Log.d(MainActivity::class.java.simpleName, movies.toString())
                } catch (e: Exception) {
                    Log.d(MainActivity::class.java.simpleName, e.localizedMessage)
                }
            }

            override fun onFailure(call: Call<MovieResponse?>?, t: Throwable) {
                Log.d(MainActivity::class.java.simpleName, t.localizedMessage)
            }
        })
    }

    internal fun getMovies(): LiveData<ArrayList<Movie>> {
        return movieList
    }
}