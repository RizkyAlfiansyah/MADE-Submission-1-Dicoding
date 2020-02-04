package com.rizky.made.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizky.made.BuildConfig.IMAGE_URL
import com.rizky.made.view.MainActivity
import com.rizky.made.R
import com.rizky.made.model.TvShow
import com.rizky.made.view.DetailTvActivity
import kotlinx.android.synthetic.main.inflater_film.view.*

class TvShowAdapter: RecyclerView.Adapter<TvShowAdapter.ListViewHolder>() {

    private val movieList = ArrayList<TvShow>()

    fun setData(items: ArrayList<TvShow>) {
        movieList.clear()
        movieList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.inflater_film, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("StringFormatInvalid")
        fun bind(tv: TvShow) {
            with(itemView) {
                tv_film_title.text = tv.title
                Glide.with(itemView).load(IMAGE_URL + tv.poster)
                    .into(iv_film_poster)
            }
            itemView.setOnClickListener {
                val mIntent = Intent(itemView.context, DetailTvActivity::class.java)
                mIntent.putExtra(MainActivity.DATA_EXTRA, tv.id)
                itemView.context.startActivity(mIntent)
            }
        }
    }
}