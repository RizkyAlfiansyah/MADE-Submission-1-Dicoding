package com.rizky.madefinalsubmission.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import com.rizky.madefinalsubmission.db.MovieDatabase
import com.rizky.madefinalsubmission.model.Movie

class FavMovieProvider : ContentProvider() {
    private lateinit var movieDatabase: MovieDatabase

    companion object{

        const val AUTHORITY = "com.rizky.madefinalsubmission"
        const val SCHEME = "content"
        const val MOVIE_TABLE = "movie"
        const val MOVIE_DIR = 1

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(
                AUTHORITY,
                MOVIE_TABLE,
                MOVIE_DIR
            )
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val data = values?.getAsInteger("id")?.let {
            Movie(
                id = it,
                poster = values.getAsString("poster_path"),
                title = values.getAsString("title"),
                description = values.getAsString("overview"),
                releaseDate = values.getAsString("release_date"),
                voteAverage = values.getAsString("vote_average"),
                originalLanguage = values.getAsString("original_language")
            )
        }
        val result = data?.let { movieDatabase.getMovieDAO().insertMovie(it) }
        context?.contentResolver?.notifyChange(uri, null)
        return result?.let { ContentUris.withAppendedId(uri, it) }
    }

    override fun onCreate(): Boolean {
        movieDatabase = Room.databaseBuilder(
            context!!.applicationContext,
            MovieDatabase::class.java,
            "db_movie"
        )
            .allowMainThreadQueries()
            .build()

        return true
    }


    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when(sUriMatcher.match(uri)){
            MOVIE_DIR -> {
                movieDatabase.getMovieDAO().selectAll()
            }
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw UnsupportedOperationException("Not yet implemented")
    }
}