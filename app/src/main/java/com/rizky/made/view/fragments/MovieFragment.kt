package com.rizky.made.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.rizky.made.database.MovieDatabase
import com.rizky.made.R
import com.rizky.made.adapter.MovieAdapter
import com.rizky.made.helper.ViewModelHelpers
import com.rizky.made.model.Movie
import com.rizky.made.view.MainActivity
import com.rizky.made.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.fragment_movie_frament.*

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment(), ViewModelHelpers {

    private lateinit var adapter: MovieAdapter
    private lateinit var movieListViewModel: MovieListViewModel
    private lateinit var movieDatabase: MovieDatabase
    private var fragment_type: String? = "fragment type"

    private var movieList = ArrayList<Movie>()
    private var successLoad = false
    private var errorMessage = ""

    private var isFavorite = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_frament, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments != null) {
            isFavorite = arguments!!.getBoolean("favorite", false)
        }
        adapter = MovieAdapter(movieList)
        rv_list.adapter = adapter
        rv_list.layoutManager = GridLayoutManager(activity, 3)

        movieDatabase = MovieDatabase.getInstance(context!!.applicationContext) as MovieDatabase

        fragment_type = arguments?.getString(MainActivity.BUNDLE_EXTRA)
        if (fragment_type.equals(MainActivity.MOVIE)) {
            rv_list.adapter = adapter
        }

        movieListViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MovieListViewModel::class.java)
        movieListViewModel.setViewModelHelpers(this)
        movieListViewModel.getMovies().observe(viewLifecycleOwner, Observer {
            if (it != null) {

                movieList.clear()
                movieList.addAll(it)
                adapter.notifyDataSetChanged()

                showList(true)
                showError(false, "")
                showLoading(false)
            }
        })

        if (savedInstanceState == null) {
            loadData()
        } else {
            errorMessage = savedInstanceState.getString(MainActivity.ERROR_MESSAGE_STATE).toString()
            successLoad = savedInstanceState.getBoolean(MainActivity.SUCCESS_STATE)

            if (successLoad) {
                movieList.clear()
                movieList.addAll(savedInstanceState.getParcelableArrayList(MainActivity.MOVIE_STATE)!!)
                adapter.notifyDataSetChanged()
            } else {
                showLoading(false)
                showList(false)
                showError(!successLoad, errorMessage)
            }
        }

        btn_reload.setOnClickListener {
            loadData()
        }

        if (isFavorite) {
            setHasOptionsMenu(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.movie_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_reload -> {
                loadData()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(MainActivity.MOVIE_STATE, movieList)
        outState.putBoolean(MainActivity.SUCCESS_STATE, successLoad)
        outState.putString(MainActivity.ERROR_MESSAGE_STATE, errorMessage)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun showList(state: Boolean) {
        if (state) {
            rv_list.visibility = View.VISIBLE
        } else {
            rv_list.visibility = View.GONE
        }
    }

    private fun showError(state: Boolean, message: String) {
        if (state) {
            layout_message.visibility = View.VISIBLE
        } else {
            layout_message.visibility = View.GONE
        }
        tv_message.text = message
    }

    private fun loadData() {
        showLoading(true)
        showList(false)
        showError(false, "")

        if (isFavorite) {
            movieListViewModel.setFavoriteMovies(movieDatabase.movieDB())
        } else {
            movieListViewModel.setMovies()
        }
    }

    override fun onFailure(message: String) {
        showLoading(false)
        showList(false)
        showError(true, message)

        successLoad = false
        errorMessage = message
    }

    override fun onSuccess() {
        successLoad = true
    }

}
