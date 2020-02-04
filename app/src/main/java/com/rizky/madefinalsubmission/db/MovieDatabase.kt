package com.rizky.madefinalsubmission.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rizky.madefinalsubmission.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDAO(): MovieDAO
}