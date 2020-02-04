package com.rizky.madefinalsubmission.view

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.room.Room
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.rizky.madefinalsubmission.BuildConfig
import com.rizky.madefinalsubmission.BuildConfig.IMAGE_URL
import com.rizky.madefinalsubmission.R
import com.rizky.madefinalsubmission.db.MovieDAO
import com.rizky.madefinalsubmission.db.MovieDatabase
import com.rizky.madefinalsubmission.model.Movie
import com.rizky.madefinalsubmission.widget.FavMovieWidget
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    @BindView(R.id.toolbar)
    @JvmField
    var toolbar: Toolbar? = null
    @BindView(R.id.tv_title)
    @JvmField
    var title: TextView? = null
    @BindView(R.id.tv_description)
    @JvmField
    var description: TextView? = null
    @BindView(R.id.tv_language)
    @JvmField
    var originalLanguage: TextView? = null
    @BindView(R.id.tv_release)
    @JvmField
    var releaseDate: TextView? = null
    @BindView(R.id.tv_vote)
    @JvmField
    var voteAverage: TextView? = null
    @BindView(R.id.iv_poster)
    @JvmField
    var poster: ImageView? = null
    private var movie: Movie? = null
    private var movieDAO: MovieDAO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val intent = intent
        movie = intent.getParcelableExtra(MainActivity.DATA_EXTRA)
        ButterKnife.bind(this)

        initToolbar()
        loadData()
        showDetails(movie)

        movieDAO = Room.databaseBuilder(this, MovieDatabase::class.java, "db_movie")
            .allowMainThreadQueries()
            .build()
            .getMovieDAO()
    }

    private fun initToolbar() {
        layout_movie.visibility = View.VISIBLE
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        setTitle(R.string.detailfilm)
    }

    private fun loadData(){
        progressBar.visibility = View.VISIBLE
        layout_movie.visibility = View.VISIBLE

    }

    private fun showDetails(movie: Movie?) {
        val handler = Handler()
        Thread(Runnable {
            try {
                Thread.sleep(500)
            } catch (e: Exception) {
            }
            handler.post {
                title?.text = movie?.title
                voteAverage?.text = movie?.voteAverage
                description?.text = movie?.description
                releaseDate?.text = movie?.releaseDate
                originalLanguage?.text = movie?.originalLanguage
                poster?.let {
                    Glide.with(this@DetailActivity)
                        .load(IMAGE_URL+ movie?.poster)
                        .into(it)
                }
                progressBar.visibility = View.INVISIBLE
            }
        }).start()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        if (movieDAO?.getMovieByTitle(movie?.title)!! > 0) {
            setFavoriteSelected(menu?.getItem(0))
        }
        return true
    }

    private fun setFavoriteSelected(item: MenuItem?) {
        item?.setIcon(R.drawable.ic_favorite_white_24dp)
        item?.isEnabled = false
    }

    private fun markAsFavorite() {
        movie?.let { movieDAO?.insertMovie(it) }
        setResult(Activity.RESULT_OK)
        val brIntent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        sendBroadcast(brIntent)
        FavMovieWidget.updateWidget(this@DetailActivity)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
            R.id.menu_favorite -> {
                try {
                    markAsFavorite()
                    setFavoriteSelected(item)
                    Toast.makeText(this, R.string.favorite_add_success, Toast.LENGTH_SHORT).show()
                } catch (e: SQLiteConstraintException) {
                    Toast.makeText(this, e.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
        return true
    }
}
