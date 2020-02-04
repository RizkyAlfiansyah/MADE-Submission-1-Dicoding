package com.rizky.made.network

import com.rizky.made.model.Movie
import com.rizky.made.model.MovieResponse
import com.rizky.made.model.TvShow
import com.rizky.made.model.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("discover/movie")
    fun DiscoverMovie(@Query("language") language: String): Call<MovieResponse>

    @GET("discover/tv")
    fun DiscoverTV(@Query("language") language: String): Call<TvShowResponse>

    @GET("movie/{movieid}")
    fun GetMovie(@Path("movieid") id: Int, @Query("language") language: String): Call<Movie>

    @GET("tv/{tvid}")
    fun GetTV(@Path("tvid") id: Int, @Query("language") language: String): Call<TvShow>
}