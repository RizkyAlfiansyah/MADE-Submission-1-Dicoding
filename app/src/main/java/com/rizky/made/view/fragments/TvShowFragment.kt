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
import com.rizky.made.adapter.TvShowAdapter
import com.rizky.made.view.MainActivity
import com.rizky.made.viewmodel.TvShowListViewModel
import kotlinx.android.synthetic.main.fragment_movie.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : Fragment() {

    private lateinit var adapter: TvShowAdapter
    private lateinit var tvListViewModel: TvShowListViewModel
    private lateinit var recyclerView : RecyclerView
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

        adapter = TvShowAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(activity, 3)

        fragment_type = arguments?.getString(MainActivity.BUNDLE_EXTRA)
        if (fragment_type.equals(MainActivity.MOVIE)) {
            recyclerView.adapter = adapter
        }

        tvListViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(TvShowListViewModel::class.java)
        tvListViewModel.getTV().observe(this, Observer {
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
        tvListViewModel.setTV()
    }
}
