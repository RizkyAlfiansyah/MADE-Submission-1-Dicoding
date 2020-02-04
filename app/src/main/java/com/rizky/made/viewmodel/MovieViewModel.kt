package com.rizky.made.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizky.made.model.Movie

class MovieViewModel : ViewModel() {
    private val movie = MutableLiveData<Movie>()

    internal fun setMovie(item: Movie) {
        movie.postValue(item)
    }

    internal fun getMovie(): LiveData<Movie> {
        return movie
    }
}