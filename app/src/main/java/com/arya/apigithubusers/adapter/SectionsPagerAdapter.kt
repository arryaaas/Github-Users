package com.arya.apigithubusers.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.arya.apigithubusers.R
import com.arya.apigithubusers.fragment.FollowFragment

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var username: String? = null

    @StringRes
    private val tabTitle = intArrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2
    )

    override fun getItem(position: Int): Fragment {
        return FollowFragment.newInstance(position + 1, username.toString())
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabTitle[position])
    }

    override fun getCount(): Int {
        return 2
    }

}