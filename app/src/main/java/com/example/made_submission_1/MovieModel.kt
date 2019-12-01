package com.example.made_submission_1

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel( var photo : Int? = null, var name: String? = null,
                       var description : String? = null, var rating : String? = null,
                       var status : String? = null): Parcelable