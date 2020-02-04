package com.rizky.madefinalsubmission.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movie", indices = [Index(value = ["title"], unique = true)])
data class Movie(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName(value = "title", alternate = ["name"])
    var title: String? = null,
    @SerializedName(value = "release_date", alternate = ["first_air_date"])
    var releaseDate: String? = null,
    @SerializedName("overview")
    var description: String? = null,
    @SerializedName("vote_average")
    var voteAverage: String? = null,
    @SerializedName("original_language")
    var originalLanguage: String? = null,
    @SerializedName("poster_path")
    var poster: String? = null,
    @SerializedName("media_type")
    var movieType: String? = null
) : Parcelable