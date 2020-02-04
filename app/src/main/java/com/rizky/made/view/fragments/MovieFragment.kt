package com.rizky.made.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizky.made.R
import com.rizky.made.adapter.MovieAdapter
import com.rizky.made.view.MainActivity
import com.rizky.made.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.fragment_movie.*

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {

    private lateinit var adapter: MovieAdapter
    private lateinit var movieListViewModel: MovieListViewModel
    private lateinit var recyclerView: RecyclerView
    private var fragment_type: String? = "fragment type"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_list)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = MovieAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(activity, 3)

        fragment_type = arguments?.getString(MainActivity.BUNDLE_EXTRA)
        if (fragment_type.equals(MainActivity.MOVIE)) {
            recyclerView.adapter = adapter
        }

        movieListViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MovieListViewModel::class.java)
        movieListViewModel.getMovies().observe(this, Observer {
            if (it != null) {
                adapter.setData(it)
                showLoading(false)
            }
        })

        loadData()
    }

    private fun showLoading(state: Boolean) {
        if (progressBar != null) {
            if (state) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun loadData() {
        showLoading(true)
        movieListViewModel.setMovies()
    }


}
