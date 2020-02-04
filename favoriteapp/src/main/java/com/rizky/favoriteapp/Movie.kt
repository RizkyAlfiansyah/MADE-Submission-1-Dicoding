package com.rizky.favoriteapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var id: Int = 0,
    var title: String? = null,
    var releaseDate: String? = null,
    var description: String? = null,
    var voteAverage: String? = null,
    var originalLanguage: String? = null,
    var poster: String? = null,
    var movieType: String? = null
) : Parcelable