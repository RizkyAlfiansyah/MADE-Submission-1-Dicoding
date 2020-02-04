package com.rizky.made.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rizky.made.R
import com.rizky.made.adapter.FavoriteAdapter
import com.rizky.made.view.MainActivity
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    private lateinit var adapter: FavoriteAdapter
    private var fragmentList = ArrayList<Fragment>()
    private var fragment_type: String? = "fragment type"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteAdapter(
            childFragmentManager,
            context!!,
            fragmentList
        )
        viewPager.adapter = adapter

        fragment_type = arguments?.getString(MainActivity.BUNDLE_EXTRA)
        if (fragment_type.equals(MainActivity.FAVORITE)) {
            viewPager.adapter = adapter
        }

        tabs.setupWithViewPager(viewPager)

        val bundle = Bundle()
        bundle.putBoolean(MainActivity.FAVORITE_STATE, true)
        var fragment = MovieFragment()
        fragment.arguments = bundle
        fragmentList.add(fragment)

        var fragment2 = TvShowFragment()
        fragment2.arguments = bundle
        fragmentList.add(fragment2)
        adapter.notifyDataSetChanged()
    }
}
