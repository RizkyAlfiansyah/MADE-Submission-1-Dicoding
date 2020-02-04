package com.rizky.madefinalsubmission.network

import com.rizky.madefinalsubmission.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/{type}")
    fun getMovies(@Path("type") movieType: String?): Call<MovieResponse?>

    @GET("search/multi")
    fun searchMovies(@Query("query") query: String?): Call<MovieResponse?>

    @GET("discover/movie")
    fun getReleasedMovies(@Query("primary_release_date.gte") date: String?, @Query("primary_release_date.lte") today: String?): Call<MovieResponse?>

}
