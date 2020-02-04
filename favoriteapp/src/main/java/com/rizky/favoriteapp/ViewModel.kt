package com.rizky.favoriteapp

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModel: ViewModel() {
    private val movieList = MutableLiveData<ArrayList<Movie>>()

    companion object{
        val CONTENT_URI: Uri = Uri.Builder().scheme("content")
            .authority("com.rizky.madefinalsubmission")
            .appendPath("movie")
            .build()
    }

    internal fun setData(context: Context){
        val cur = context.contentResolver.query(
            CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val result = ArrayList<Movie>()
        if (cur != null) {
            while(cur.moveToNext()){
                val data = Movie(
                    id = cur.getInt(cur.getColumnIndexOrThrow("id")),
                    poster = cur.getString(cur.getColumnIndexOrThrow("poster")),
                    title = cur.getString(cur.getColumnIndexOrThrow("title")),
                    description = cur.getString(cur.getColumnIndexOrThrow("description")),
                    releaseDate = cur.getString(cur.getColumnIndexOrThrow("releaseDate")),
                    voteAverage = cur.getString(cur.getColumnIndexOrThrow("voteAverage")),
                    originalLanguage = cur.getString(cur.getColumnIndexOrThrow("originalLanguage"))
                )
                result.add(data)
            }
        }
        movieList.postValue(result)
    }

    internal fun getData(): LiveData<ArrayList<Movie>> = movieList
}