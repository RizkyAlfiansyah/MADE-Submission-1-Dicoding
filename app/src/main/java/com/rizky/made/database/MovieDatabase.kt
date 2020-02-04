package com.rizky.made.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rizky.made.model.Movie
import com.rizky.made.model.TvShow

@Database(entities = arrayOf(Movie::class, TvShow::class), version = 2, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDB(): MovieDB
    abstract fun tvDB(): TvDB

    companion object {

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase? {
            if (INSTANCE == null) {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        "moviecatalogue.db"
                    ).build()
                    INSTANCE = instance
                }
            }
            return INSTANCE
        }
    }
}