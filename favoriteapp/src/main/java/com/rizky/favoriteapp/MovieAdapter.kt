package com.rizky.favoriteapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item.view.*

class MovieAdapter internal constructor(private var movieList: ArrayList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView) {
                title_fav.text = movie.title
                if (movie.description?.isBlank()!!) {
                    description_fav.text = resources.getString(R.string.not_available)
                } else {
                    description_fav.text = movie.description
                }
                Glide.with(itemView).load("https://image.tmdb.org/t/p/w92" + movie.poster)
                    .into(poster_fav)
            }
            itemView.setOnClickListener {

            }
        }
    }
}