package com.rizky.favoriteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val movieFragment = MovieFragment()
        val bundle = Bundle()
        movieFragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFrame, movieFragment, MovieFragment::class.java.simpleName)
            .commit()
    }
}
