package com.rizky.made.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rizky.made.R

class FavoriteAdapter internal constructor(
    fm: FragmentManager,
    private val context: Context,
    private val fragmentList: ArrayList<Fragment>
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.resources.getString(R.string.title_movies)
            1 -> context.resources.getString(R.string.title_tv_shows)
            else -> ""
        }
    }
}