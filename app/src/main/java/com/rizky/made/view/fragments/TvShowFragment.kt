package com.rizky.made.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.rizky.made.R
import com.rizky.made.adapter.TvShowAdapter
import com.rizky.made.database.MovieDatabase
import com.rizky.made.helper.ViewModelHelpers
import com.rizky.made.model.TvShow
import com.rizky.made.view.MainActivity
import com.rizky.made.viewmodel.TvShowListViewModel
import kotlinx.android.synthetic.main.fragment_movie_frament.*
import kotlinx.android.synthetic.main.fragment_tv_show.progressBar
import kotlinx.android.synthetic.main.fragment_tv_show.rv_list

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : Fragment(), ViewModelHelpers {

    private lateinit var adapter: TvShowAdapter
    private lateinit var tvListViewModel: TvShowListViewModel
    private lateinit var movieDatabase: MovieDatabase
    private var fragment_type: String? = "fragment type"

    private var tvList = ArrayList<TvShow>()
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
        adapter = TvShowAdapter(tvList)
        rv_list.adapter = adapter
        rv_list.layoutManager = GridLayoutManager(activity,3)

        movieDatabase = MovieDatabase.getInstance(context!!.applicationContext) as MovieDatabase

        fragment_type = arguments?.getString(MainActivity.BUNDLE_EXTRA)
        if (fragment_type.equals(MainActivity.TVSHOW)) {
            rv_list.adapter = adapter
        }

        tvListViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(TvShowListViewModel::class.java)
        tvListViewModel.setViewModelHelpers(this)
        tvListViewModel.getTV().observe(viewLifecycleOwner, Observer {
            if (it != null) {

                tvList.clear()
                tvList.addAll(it)
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
                tvList.clear()
                tvList.addAll(savedInstanceState.getParcelableArrayList(MainActivity.MOVIE_STATE)!!)
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
        outState.putParcelableArrayList(MainActivity.MOVIE_STATE, tvList)
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
            tvListViewModel.setFavoriteTV(movieDatabase.tvDB())
        } else {
            tvListViewModel.setTV()
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