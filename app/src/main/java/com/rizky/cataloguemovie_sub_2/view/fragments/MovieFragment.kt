package com.rizky.cataloguemovie_sub_2.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizky.cataloguemovie_sub_2.model.Movie
import com.rizky.cataloguemovie_sub_2.adapter.MovieAdapter
import com.rizky.cataloguemovie_sub_2.R
import com.rizky.cataloguemovie_sub_2.view.DetailActivity
import com.rizky.cataloguemovie_sub_2.view.MainActivity
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {
    private lateinit var adapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView
    private var datas = mutableListOf<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recyclerview)


        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val type = arguments?.getString(MainActivity.BUNDLE_EXTRA)
        if (type.equals(MainActivity.MOVIE)) {
            initData(MainActivity.MOVIE)
        } else if (type.equals(MainActivity.TVSHOW)) {
            initData(MainActivity.TVSHOW)
        }

        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.HORIZONTAL))
        recyclerView.layoutManager = GridLayoutManager(activity, 3)
        adapter = MovieAdapter(activity!!, datas) {
            startActivity<DetailActivity>(MainActivity.DATA_EXTRA to it)
        }
        recyclerView.adapter = adapter

        super.onActivityCreated(savedInstanceState)
    }

    fun initData(type: String) {
        if (type.equals(MainActivity.MOVIE)) {
            val titles = resources.getStringArray(R.array.movie_titles)
            val descriptions = resources.getStringArray(R.array.movie_descriptions)
            val posters = resources.obtainTypedArray(R.array.movie_posters)
            val releaseDates = resources.getStringArray(R.array.movie_releasedates)
            val runningTimes = resources.getStringArray(R.array.movie_runningtimes)
            val distributedBy = resources.getStringArray(R.array.movie_distributedby)
            datas.clear()
            for (i in titles.indices) {
                datas.add(
                    Movie(
                        titles[i],
                        descriptions[i],
                        posters.getResourceId(i, 0),
                        releaseDates[i],
                        runningTimes[i],
                        distributedBy[i]
                    )
                )
            }
            posters.recycle()
        } else if (type.equals(MainActivity.TVSHOW)) {
            val titles = resources.getStringArray(R.array.tvshow_titles)
            val descriptions = resources.getStringArray(R.array.tvshow_descriptions)
            val posters = resources.obtainTypedArray(R.array.tvshow_posters)
            val releaseDates = resources.getStringArray(R.array.tvshow_releasedates)
            val runningTimes = resources.getStringArray(R.array.tvshow_runningtimes)
            val distributedBy = resources.getStringArray(R.array.tvshow_distributedby)
            datas.clear()
            for (i in titles.indices) {
                datas.add(
                    Movie(
                        titles[i],
                        descriptions[i],
                        posters.getResourceId(i, 0),
                        releaseDates[i],
                        runningTimes[i],
                        distributedBy[i]
                    )
                )
            }
            posters.recycle()
        }
    }


}
