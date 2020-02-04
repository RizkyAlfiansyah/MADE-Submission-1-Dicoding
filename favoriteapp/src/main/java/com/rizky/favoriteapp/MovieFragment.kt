package com.rizky.favoriteapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_movie.*

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {

    private lateinit var adapter: MovieAdapter
    private lateinit var viewModel: ViewModel

    private val movieList = ArrayList<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = MovieAdapter(movieList)
        rv_list.adapter = adapter
        rv_list.layoutManager = LinearLayoutManager(context)
        rv_list.setHasFixedSize(true)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(ViewModel::class.java)
        viewModel.getData().observe(viewLifecycleOwner, Observer{
            movieList.clear()
            movieList.addAll(it)
            adapter.notifyDataSetChanged()
        })

        context?.let { viewModel.setData(it) }
    }
}
