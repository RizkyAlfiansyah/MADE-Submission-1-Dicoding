package com.example.made_submission_1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MovieAdapter(private val context: Context, private val listMovie : ArrayList<MovieModel>) : BaseAdapter() {


    override fun getView(position: Int, p1: View?, viewGroup: ViewGroup): View? {


        var view = p1
        if(view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_movie, null, true)
        }

        val viewHolder = view?.let { ViewHolder(it) }
        val movie = getItem(position) as MovieModel
        viewHolder?.bind(movie)

        return view
    }

    private inner class ViewHolder internal constructor(view: View) {

        private val txtName: TextView = view.findViewById(R.id.txt_name)
        private val txtDescription: TextView = view.findViewById(R.id.txt_description)
        private val imgPhoto: ImageView = view.findViewById(R.id.img_photo)

        internal fun bind(movie: MovieModel) {
            txtName.text = movie.name
            txtDescription.text = movie.description
            movie.photo?.let { imgPhoto.setImageResource(it) }
        }
    }


    override fun getItem(i: Int): Any {
        return listMovie[i]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return listMovie.size
    }

}