package com.rizky.made.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "poster")
    @SerializedName("poster_path")
    var poster: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "overview")
    var overview: String,
    @ColumnInfo(name = "releaseDate")
    @SerializedName("release_date")
    var releaseDate: String,
    @ColumnInfo(name = "vote")
    @SerializedName("vote_average")
    var voteAverage: Double,
    @ColumnInfo(name = "runtime")
    var runtime: Int
) : Parcelable