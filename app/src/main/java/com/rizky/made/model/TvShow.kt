package com.rizky.made.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShow(
    var id: Int,
    @SerializedName("poster_path")
    var poster: String,
    @SerializedName("name")
    var title: String,
    var overview: String,
    @SerializedName("first_air_date")
    var releaseDate: String,
    @SerializedName("vote_average")
    var voteAverage: Double,
    @SerializedName("genre_ids")
    var genreId: List<Int>,
    @SerializedName("episode_run_time")
    var runtime: List<Int>
) : Parcelable