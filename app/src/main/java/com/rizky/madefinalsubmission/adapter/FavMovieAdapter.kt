package com.rizky.madefinalsubmission.adapter

import android.app.AlertDialog
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.rizky.madefinalsubmission.BuildConfig
import com.rizky.madefinalsubmission.R
import com.rizky.madefinalsubmission.db.MovieDatabase
import com.rizky.madefinalsubmission.model.Movie
import com.rizky.madefinalsubmission.widget.FavMovieWidget
import java.util.ArrayList

class FavMovieAdapter(private val context: Context?) : RecyclerView.Adapter<FavMovieAdapter.FavMovieViewHolder?>() {
    private var movies: MutableList<Movie> = ArrayList()
    fun setMovies(movies: ArrayList<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavMovieViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.list_fav_item, parent, false)
        return FavMovieViewHolder(mView)
    }

    override fun getItemCount():  Int = movies.size

    override fun onBindViewHolder(holder: FavMovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }


    inner class FavMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.title_fav)
        @JvmField
        var title: TextView? = null
        @BindView(R.id.description_fav)
        @JvmField
        var description: TextView? = null
        @BindView(R.id.poster_fav)
        @JvmField
        var poster: ImageView? = null
        @BindView(R.id.btn_delete)
        @JvmField
        var btnDelete: Button? = null

        fun bind(movie: Movie?) {
            if (context != null) {
                poster?.let {
                    Glide.with(context)
                        .load(BuildConfig.IMAGE_URL + movie?.poster)
                        .apply(RequestOptions())
                        .into(it)
                }
            }
            title?.text = movie?.title
            description?.text = movie?.description
            btnDelete?.setOnClickListener { v ->
                val builder = AlertDialog.Builder(itemView.context)
                builder.setTitle(R.string.confirm_title)
                builder.setMessage(R.string.confirm_message)
                builder.setCancelable(false)
                builder.setPositiveButton(R.string.pos_btn) { _, _ ->
                    val movieDAO = Room.databaseBuilder(
                        itemView.context,
                        MovieDatabase::class.java,
                        "db_movie"
                    )
                        .allowMainThreadQueries()
                        .build()
                        .getMovieDAO()
                    movies[adapterPosition].id.let { movieDAO.deleteByUid(it) }
                    movies.remove(movie)
                    notifyDataSetChanged()
                    val brIntent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
                    context?.sendBroadcast(brIntent)
                    Snackbar.make(v, R.string.success_info, Snackbar.LENGTH_SHORT).show()
                    if (context != null) {
                        FavMovieWidget.updateWidget(context)
                    }
                }
                builder.setNegativeButton(R.string.neg_btn) { _, _ -> builder.setCancelable(true) }
                builder.show()
            }
        }

        init {
            ButterKnife.bind(this, itemView)
        }
    }

}