package com.rizky.madefinalsubmission.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rizky.madefinalsubmission.R
import com.rizky.madefinalsubmission.view.fragment.FavoriteFragment
import com.rizky.madefinalsubmission.view.fragment.MovieFragment
import com.rizky.madefinalsubmission.view.fragment.SearchFragment
import com.rizky.madefinalsubmission.view.fragment.TvShowFragment

class MainActivity : AppCompatActivity() {

    companion object{
        var DATA_EXTRA = "movie"
    }

    @BindView(R.id.toolbar)
    @JvmField
    var toolbar: Toolbar? = null
    @BindView(R.id.bottom_navigation)
    @JvmField
    var navigation: BottomNavigationView? = null
    private lateinit var mSearchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        navigation?.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        if (supportActionBar != null) {
            supportActionBar?.elevation = 0f
            supportActionBar?.setTitle(R.string.title_movies)
        }
        if (savedInstanceState == null) {
            loadFragment(MovieFragment())
        }
    }

    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener? = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        val fragment: Fragment
        toolbar?.collapseActionView()
        when (menuItem.itemId) {
            R.id.navigation_movies -> {
                supportActionBar?.setTitle(R.string.title_movies)
                fragment =
                    MovieFragment()
                loadFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_tvshows -> {
                supportActionBar?.setTitle(R.string.title_tv_shows)
                fragment =
                    TvShowFragment()
                loadFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                supportActionBar?.setTitle(R.string.title_favorite)
                fragment =
                    FavoriteFragment()
                loadFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun loadFragment(fragment: Fragment?) {
        val transaction = supportFragmentManager.beginTransaction()
        if (fragment != null) {
            transaction.replace(R.id.mainFrame, fragment)
        }
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        if (menu != null) {
            mSearchView = menu.findItem(R.id.search_menu).actionView as SearchView
        }
        setupSearchView()
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupSearchView() {
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String?): Boolean {
                mSearchView?.clearFocus()
                return false
            }

            override fun onQueryTextChange(s: String?): Boolean {
                if (s != null) {
                    if (s.isNotEmpty()) {
                        val transaction = supportFragmentManager.beginTransaction()
                        val bundle = Bundle()
                        bundle.putString("query", s)
                        val fragment =
                            SearchFragment()
                        fragment.arguments = bundle
                        transaction.replace(R.id.mainFrame, fragment)
                        transaction.commit()
                        navigation?.menu?.setGroupCheckable(0, false, true)
                    } else {
                        navigation?.menu?.setGroupCheckable(0, true, true)
                        navigation?.menu?.getItem(0)?.isChecked
                        loadFragment(MovieFragment())
                        if (supportActionBar != null) {
                            toolbar?.setTitle(R.string.title_movies)
                        }
                    }
                }
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.setting_localization -> {
                    val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                    startActivity(mIntent)
                }
                R.id.alarm_menu -> {
                    val intent = Intent(this@MainActivity, NotificationSettingActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
