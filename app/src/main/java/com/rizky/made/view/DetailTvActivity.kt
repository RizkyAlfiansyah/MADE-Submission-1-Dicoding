package com.rizky.made.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rizky.made.*
import com.rizky.made.BuildConfig.IMAGE_URL
import com.rizky.made.network.ApiRepository
import com.rizky.made.network.ApiServices
import com.rizky.made.model.TvShow
import com.rizky.made.viewmodel.TvShowViewModel
import kotlinx.android.synthetic.main.activity_detail_movie.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTvActivity : AppCompatActivity() {

    private lateinit var call: Call<TvShow>
    private lateinit var tvViewModel: TvShowViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val tvId = intent.getIntExtra(MainActivity.DATA_EXTRA, 0)
        setContentView(R.layout.activity_detail_movie)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        title = resources.getString(R.string.detailfilm)

        textView4.text = resources.getString(R.string.runningtime)

        tvViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(TvShowViewModel::class.java)
        tvViewModel.getTV().observe(this, Observer {
            if (it != null) {
                progressBar.visibility = View.GONE
                showMovie(it)
            }
        })

        val service = ApiRepository.createService(
            ApiServices::class.java
        )
        call = service.GetTV(tvId, resources.getString(R.string.data_language))

        loadData()
    }

    private fun loadData() {
        layout_movie.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE
        if (call.isExecuted) {
            return
        }
        call.enqueue(object : Callback<TvShow> {
            override fun onFailure(call: Call<TvShow>, t: Throwable) {
                progressBar.visibility = View.GONE
            }

            override fun onResponse(call: Call<TvShow>, response: Response<TvShow>) {
                progressBar.visibility = View.GONE
                val tv = response.body() as TvShow
                tvViewModel.setTV(tv)
            }
        })
    }

    private fun showMovie(movie: TvShow) {
        layout_movie.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

        tv_title.text = movie.title
        tv_description.text = movie.overview
        tv_release.text = movie.releaseDate
        tv_vote.text = movie.voteAverage.toString()
        tv_runtime.text = movie.runtime[0].toString()
        Glide.with(this@DetailTvActivity).load(IMAGE_URL + movie.poster)
            .into(iv_poster)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
