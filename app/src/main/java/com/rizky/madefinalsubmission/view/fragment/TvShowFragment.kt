package com.rizky.madefinalsubmission.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
class TvShowFragment : Fragment() {

    @BindView(R.id.rv_list) lateinit var listTvShow: RecyclerView
    @BindView(R.id.shimmer_view_container)
    @JvmField var mShimmerViewContainer: ShimmerFrameLayout? = null
    private lateinit var adapter: MovieAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_movie, container, false)
        ButterKnife.bind(this, rootView)
        listTvShow.layoutManager = GridLayoutManager(activity, 3)
        adapter = MovieAdapter(activity)
        listTvShow.adapter = adapter
        mShimmerViewContainer?.startShimmer()
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)
        mainViewModel.setMovies("tv")
        mainViewModel.getMovies().observe(viewLifecycleOwner, getMovies)
        return rootView
    }

    private val getMovies: Observer<ArrayList<Movie>> = Observer { movies ->
        if (movies != null) {
            mShimmerViewContainer?.stopShimmer()
            mShimmerViewContainer?.visibility = View.GONE
            adapter.setMovies(movies)
        }
    }

}
