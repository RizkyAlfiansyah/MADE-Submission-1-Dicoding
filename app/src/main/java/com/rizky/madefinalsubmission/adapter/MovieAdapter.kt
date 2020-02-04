package com.rizky.madefinalsubmission.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rizky.madefinalsubmission.BuildConfig
import com.rizky.madefinalsubmission.R
import com.rizky.madefinalsubmission.view.DetailActivity
import com.rizky.madefinalsubmission.view.MainActivity
import com.rizky.madefinalsubmission.model.Movie
import java.util.ArrayList

class MovieAdapter(private val context: FragmentActivity?) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var movies: ArrayList<Movie> = ArrayList()
    fun setMovies(movies: ArrayList<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MovieViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.inflater_film, viewGroup, false)
        return MovieViewHolder(mView)
    }

    override fun onBindViewHolder(movieViewHolder: MovieViewHolder, i: Int) {
        movieViewHolder.bind(movies[i])
    }

    override fun getItemCount(): Int = movies.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.tv_film_title)
        @JvmField
        var movieTitle: TextView? = null
        @BindView(R.id.iv_film_poster)
        @JvmField
        var poster: ImageView? = null

        fun bind(movie: Movie?) {
            if (context != null) {
                poster?.let {
                    Glide.with(context)
                        .load(BuildConfig.IMAGE_URL + movie?.poster)
                        .apply(RequestOptions())
                        .into(it)
                }
            }
            movieTitle?.text = movie?.title
        }

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener {
                val details = Intent(context, DetailActivity::class.java)
                details.putExtra(MainActivity.DATA_EXTRA, movies[adapterPosition])
                context?.startActivity(details)
            }
        }
    }
}