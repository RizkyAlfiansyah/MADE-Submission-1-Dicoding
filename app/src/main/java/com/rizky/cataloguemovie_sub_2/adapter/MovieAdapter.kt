package com.rizky.cataloguemovie_sub_2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizky.cataloguemovie_sub_2.R
import com.rizky.cataloguemovie_sub_2.model.Movie
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.inflater_film.view.*

class MovieAdapter(
    private val context: Context,
    private val data: MutableList<Movie>,
    private val onClickListener: (Movie) -> Unit
) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.inflater_film, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(context, data[position], onClickListener)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(
            context: Context,
            data: Movie,
            onClickListener: (Movie) -> Unit
        ) {
            itemView.tv_film_title.text = data.title
            Glide.with(context).load(data.poster).into(itemView.iv_film_poster)
            containerView.setOnClickListener { onClickListener(data) }
        }
    }

}