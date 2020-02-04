package com.rizky.madefinalsubmission.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import butterknife.BindView
import butterknife.ButterKnife

import com.rizky.madefinalsubmission.R
import com.rizky.madefinalsubmission.adapter.FavMovieAdapter
import com.rizky.madefinalsubmission.db.MovieDatabase
import com.rizky.madefinalsubmission.model.Movie
import com.rizky.madefinalsubmission.viewmodel.MainViewModel
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class FavMovieFragment : Fragment() {

    @BindView(R.id.list_fav_mov)
    @JvmField
    var listFavMov: RecyclerView? = null
    private var adapter: FavMovieAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_fav_movie, container, false)
        ButterKnife.bind(this, rootView)
        listFavMov?.layoutManager = LinearLayoutManager(activity)
        adapter = FavMovieAdapter(activity)
        listFavMov?.adapter = adapter
        val data = loadFavMovies() as ArrayList<Movie>
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)
        mainViewModel.setFavMovie(data)
        mainViewModel.getMovies().observe(viewLifecycleOwner, getMovies)
        return rootView
    }

    private fun loadFavMovies(): MutableList<Movie?>? {
        val database = activity?.let {
            Room.databaseBuilder(it, MovieDatabase::class.java, "db_movie")
                .allowMainThreadQueries()
                .build()
                .getMovieDAO()
        }
        return database?.getMoviesByMovieType("movie")
    }

    private val getMovies: Observer<ArrayList<Movie>> = Observer { movies ->
        if (movies != null) {
            adapter?.setMovies(movies)
        }
    }
}
