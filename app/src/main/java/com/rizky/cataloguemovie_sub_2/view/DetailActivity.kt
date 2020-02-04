package com.rizky.cataloguemovie_sub_2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.rizky.cataloguemovie_sub_2.model.Movie
import com.rizky.cataloguemovie_sub_2.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = "Detail Film"

        val data = intent.getParcelableExtra<Movie>(MainActivity.DATA_EXTRA)

        tv_film_title.text = data?.title
        tv_film_releasedate.text = data?.releaseDate
        tv_film_durationtime.text = data?.runningTime
        tv_film_distributedby.text = data?.distributedBy
        tv_film_description.text = data?.description
        Glide.with(applicationContext).load(data?.poster).into(iv_film_poster)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
