package com.rizky.made.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rizky.made.BuildConfig.IMAGE_URL
import com.rizky.made.R
import com.rizky.made.database.MovieDatabase
import com.rizky.made.helper.ViewModelHelpers
import com.rizky.made.model.Movie
import com.rizky.made.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailMovieActivity : AppCompatActivity(),
    ViewModelHelpers {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieDatabase: MovieDatabase

    private var movieId: Int = 0
    private var movie: Movie? = null
    private var successLoad = false
    private var errorMessage = ""
    private var isFavorite = false
    private var menuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieId = intent.getIntExtra(MainActivity.DATA_EXTRA, 0)

        setContentView(R.layout.activity_detail_movie)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        movieDatabase = MovieDatabase.getInstance(applicationContext) as MovieDatabase

        movieViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MovieViewModel::class.java)
        movieViewModel.setViewModelHelpers(this)
        movieViewModel.getMovie().observe(this, Observer {
            if (it != null) {
                movie = it
                showMovie(it)

                progressBar.visibility = View.GONE
                layout_movie.visibility = View.VISIBLE
                layout_message.visibility = View.GONE

                updateFavorite()
            }
        })

        title = ""

        if (savedInstanceState == null) {
            loadData()
        } else {
            movieId = savedInstanceState.getInt(MainActivity.DATA_EXTRA)
            successLoad = savedInstanceState.getBoolean(MainActivity.SUCCESS_STATE)
            errorMessage = savedInstanceState.getString(MainActivity.ERROR_MESSAGE_STATE).toString()
            movie = savedInstanceState.getParcelable(MainActivity.MOVIE_STATE)
            isFavorite = savedInstanceState.getBoolean(MainActivity.FAVORITE_STATE)

            if (successLoad) {
                movie?.let { showMovie(it) }
            } else {
                progressBar.visibility = View.GONE
                layout_movie.visibility = View.GONE
                layout_message.visibility = View.VISIBLE
                tv_message.text = errorMessage
            }

            updateFavorite()
        }

        favoriteState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MainActivity.DATA_EXTRA, movieId)
        outState.putParcelable(MainActivity.MOVIE_STATE, movie)
        outState.putBoolean(MainActivity.SUCCESS_STATE, successLoad)
        outState.putString(MainActivity.ERROR_MESSAGE_STATE, errorMessage)
        outState.putBoolean(MainActivity.FAVORITE_STATE, isFavorite)
    }

    private fun loadData() {
        progressBar.visibility = View.VISIBLE
        layout_movie.visibility = View.GONE
        layout_message.visibility = View.GONE

        movieViewModel.setMovie(movieId, resources.getString(R.string.data_language))
    }

    private fun showMovie(movie: Movie) {
        layout_movie.visibility = View.VISIBLE

        title = movie.title

        tv_title.text = movie.title
        if (movie.overview.isBlank()) {
            tv_description.text = resources.getString(R.string.not_available)
        } else {
            tv_description.text = movie.overview
        }
        tv_release.text = movie.releaseDate
        tv_vote.text = movie.voteAverage.toString()
        tv_runtime.text = movie.runtime.toString()
        Glide.with(this@DetailMovieActivity).load(IMAGE_URL + movie.poster)
            .into(iv_poster)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        updateFavorite()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_favorite -> {
                if (isFavorite) {
                    removeFromFavorite()
                } else {
                    addToFavorite()
                }
                isFavorite = !isFavorite
                updateFavorite()

                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addToFavorite() {
        GlobalScope.launch {
            movie?.let { movieDatabase.movieDB().insertMovie(it) }
            isFavorite = true
            withContext(Dispatchers.Main) {
                updateFavorite()
                Toast.makeText(
                    this@DetailMovieActivity,
                    resources.getString(R.string.favorite_add_success),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun removeFromFavorite() {
        GlobalScope.launch {
            movieDatabase.movieDB().deleteMovieById(movieId)
            isFavorite = false
            withContext(Dispatchers.Main) {
                updateFavorite()
                Toast.makeText(
                    this@DetailMovieActivity,
                    resources.getString(R.string.favorite_remove_success),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateFavorite() {
        if (isFavorite) {
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp)
        } else {
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp)
        }
    }

    private fun favoriteState() {
        GlobalScope.launch {
            val movieFavorite = movieDatabase.movieDB().findById(movieId)
            isFavorite = movieFavorite != null

            withContext(Dispatchers.Main) {
                updateFavorite()
            }
        }
    }

    override fun onSuccess() {
        successLoad = true
    }

    override fun onFailure(message: String) {
        successLoad = false
        errorMessage = message

        progressBar.visibility = View.GONE
        layout_movie.visibility = View.GONE
        layout_message.visibility = View.VISIBLE
        tv_message.text = errorMessage
    }
}