package com.rizky.cataloguemovie_sub_2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie (
    val title: String? = null,
    val description: String? = null,
    val poster: Int? = null,
    val releaseDate: String? = null,
    val runningTime: String? = null,
    val distributedBy: String? = null
) : Parcelable