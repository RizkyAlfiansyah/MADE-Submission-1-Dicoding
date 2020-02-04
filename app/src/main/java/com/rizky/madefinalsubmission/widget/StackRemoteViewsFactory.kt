package com.rizky.madefinalsubmission.widget

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import androidx.room.Room
import com.bumptech.glide.Glide
import com.rizky.madefinalsubmission.R
import com.rizky.madefinalsubmission.db.MovieDatabase
import com.rizky.madefinalsubmission.model.Movie

internal class StackRemoteViewsFactory(private val context: Context): RemoteViewsService.RemoteViewsFactory {
    private var movieItems = ArrayList<Movie>()

    private lateinit var movieDatabase: MovieDatabase

    override fun onCreate() {
        movieDatabase = Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            "db_movie"
        )
            .allowMainThreadQueries()
            .build()
    }


    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        movieItems.clear()
        val identityToken = Binder.clearCallingIdentity()

        val result = movieDatabase.getMovieDAO().getAllFavMovies()
        movieItems.addAll(result)

        Binder.restoreCallingIdentity(identityToken)
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName,
            R.layout.item_widget
        )
        if(movieItems.size == 0){
            return rv
        }
        val movie = movieItems[position]

        val poster = Glide.with(context)
            .asBitmap()
            .load("https://image.tmdb.org/t/p/w185" + movie.poster)
            .submit()
            .get()

        rv.setImageViewBitmap(R.id.imageView, poster)

        val extras = bundleOf(
            FavMovieWidget.EXTRA_ITEM to movie.id
        )

        val fillIntent = Intent()
        fillIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillIntent)
        return rv
    }

    override fun getCount(): Int = movieItems.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {

    }
}