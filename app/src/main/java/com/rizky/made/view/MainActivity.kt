package com.rizky.made.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import com.rizky.made.R
import com.rizky.made.view.fragments.MovieFragment
import com.rizky.made.view.fragments.TvShowFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val DATA_EXTRA = "data"
        const val BUNDLE_EXTRA = "bundle"
        const val MOVIE = "movie"
        const val TVSHOW = "tvshow"
        const val INSTANCE = "instance"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.movies -> {
                    loadMovieFragment(MOVIE)
                }
                R.id.tvshows -> {
                    loadTvFragment(TVSHOW)
                }
            }
            true
        }

        if (savedInstanceState == null) {
            bottom_navigation.selectedItemId = R.id.movies
        } else {
            when (savedInstanceState.getString(INSTANCE)) {
                MovieFragment::class.java.simpleName -> {
                    bottom_navigation.selectedItemId = R.id.movies
                }
                TvShowFragment::class.java.simpleName -> {
                    bottom_navigation.selectedItemId = R.id.tvshows
                }
            }
        }

    }

    private fun loadTvFragment(type: String) {
        val tvShowFragment = TvShowFragment()
        val bundle = Bundle()
        bundle.putString(BUNDLE_EXTRA, type)
        tvShowFragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFrame, tvShowFragment, TvShowFragment::class.java.simpleName)
            .commit()
    }

    private fun loadMovieFragment(type: String) {
        val movieFragment = MovieFragment()
        val bundle = Bundle()
        bundle.putString(BUNDLE_EXTRA, type)
        movieFragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFrame, movieFragment, MovieFragment::class.java.simpleName)
            .commit()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting_localization -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
