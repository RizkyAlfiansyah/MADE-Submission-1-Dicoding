package com.rizky.madefinalsubmission.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.facebook.shimmer.ShimmerFrameLayout

import com.rizky.madefinalsubmission.R
import com.rizky.madefinalsubmission.adapter.MovieAdapter
import com.rizky.madefinalsubmission.model.Movie
import com.rizky.madefinalsubmission.viewmodel.MainViewModel
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    @BindView(R.id.shimmer_view_container)
    @JvmField var mShimmerViewContainer: ShimmerFrameLayout? = null
    @BindView(R.id.list_mov_result) lateinit var searchResult: RecyclerView
    private lateinit var adapter: MovieAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)
        ButterKnife.bind(this, rootView)
        searchResult.layoutManager = GridLayoutManager(activity, 3)
        adapter = MovieAdapter(activity!!)
        searchResult.adapter = adapter
        mShimmerViewContainer?.startShimmer()
        val query = arguments?.getString("query")
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)
        mainViewModel.searchMovie(query)
        getMovieResult.let { mainViewModel.getMovies().observe(viewLifecycleOwner, it) }
        return rootView
    }

    private var getMovieResult: Observer<ArrayList<Movie>> = Observer { movies ->
        mShimmerViewContainer?.stopShimmer()
        mShimmerViewContainer?.visibility = View.GONE
        if (movies != null && movies.size > 0) {
            adapter.setMovies(movies)
        } else {
            Toast.makeText(context, R.string.not_found, Toast.LENGTH_LONG).show()
        }
    }


}
