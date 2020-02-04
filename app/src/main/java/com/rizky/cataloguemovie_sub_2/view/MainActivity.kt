package com.rizky.cataloguemovie_sub_2.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import com.rizky.cataloguemovie_sub_2.view.fragments.MovieFragment
import com.rizky.cataloguemovie_sub_2.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val DATA_EXTRA = "data"
        const val BUNDLE_EXTRA = "bundle"
        const val MOVIE = "movie"
        const val TVSHOW = "tvshow"
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
                    loadMovieFragment(TVSHOW)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = R.id.movies

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting_localization -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
