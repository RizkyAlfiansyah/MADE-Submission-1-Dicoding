package com.rizky.made.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var id: Int,
    @SerializedName("poster_path")
    var poster: String,
    var title: String,
    var overview: String,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("vote_average")
    var voteAverage: Double,
    var runtime: Int,
    @SerializedName("genre_ids")
    var genreId: List<Int>
) : Parcelable