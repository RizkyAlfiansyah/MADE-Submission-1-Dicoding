package com.rizky.madefinalsubmission.db

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rizky.madefinalsubmission.model.Movie

@Dao
interface MovieDAO {

    @Query("SELECT * FROM movie")
    fun selectAll(): Cursor?

    @Query("SELECT * FROM movie where id = :uid")
    fun selectById(uid: Long): Cursor?

    @Query("SELECT * FROM movie")
    fun getAllFavMovies(): MutableList<Movie>

    @Query("DELETE FROM movie WHERE id = :uid")
    fun deleteByUid(uid: Int)

    @Query("SELECT COUNT(id) FROM movie WHERE title = :title")
    fun getMovieByTitle(title: String?): Int

    @Query("SELECT * FROM movie WHERE movieType = :movieType")
    fun getMoviesByMovieType(movieType: String?): MutableList<Movie?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie) : Long
}