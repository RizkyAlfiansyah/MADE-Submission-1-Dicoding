package com.rizky.madefinalsubmission.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.tabs.TabLayout
import com.rizky.madefinalsubmission.R
import com.rizky.madefinalsubmission.adapter.ViewPagerAdapter

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    @BindView(R.id.viewPager)
    @JvmField
    var viewPager: ViewPager? = null
    @BindView(R.id.tabs)
    @JvmField
    var tabLayout: TabLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_favorite, container, false)
        ButterKnife.bind(this, rootView)
        setupViewPager(viewPager)
        tabLayout?.setupWithViewPager(viewPager)
        return rootView
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(FavMovieFragment(), resources.getString(R.string.title_movies))
        adapter.addFragment(FavTvShowFragment(), resources.getString(R.string.title_tv_shows))
        viewPager?.adapter = adapter
    }


}
