package com.arya.apigithubusers.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arya.apigithubusers.model.Follow
import com.arya.apigithubusers.R
import com.arya.apigithubusers.activity.DetailActivity
import com.arya.apigithubusers.adapter.FollowAdapter
import com.arya.apigithubusers.toast
import com.arya.apigithubusers.viewmodel.FollowViewModel
import kotlinx.android.synthetic.main.fragment_follow.*

/**
 * A simple [Fragment] subclass.
 */
class FollowFragment : Fragment() {

    private lateinit var adapter: FollowAdapter
    private lateinit var followViewModel: FollowViewModel

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_USERNAME = "username"
        fun newInstance(index: Int, username: String): FollowFragment {
            val fragment = FollowFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }

        var listFollower = ArrayList<Follow>()
        var listFollowing = ArrayList<Follow>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME)
        val activity = DetailActivity.activity

        adapter = FollowAdapter { context?.toast(it.login ?: "") }
        adapter.notifyDataSetChanged()

        rv_follow.adapter = adapter

        var index = 1
        if (arguments != null) {
            index = arguments?.getInt(ARG_SECTION_NUMBER, 0) as Int
        }

        if (activity == "Favorite") {
            if (index == 1) {
                val follower = DetailActivity.listFollowerNew
                adapter.setData(follower.toCollection(ArrayList()))
            } else {
                val following = DetailActivity.listFollowingNew
                adapter.setData(following.toCollection(ArrayList()))
            }

        } else {
            followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                FollowViewModel::class.java)

            if (index == 1) {
                followViewModel.setFollower(username ?: "")
                showLoading(true)

                followViewModel.getFollower().observe(viewLifecycleOwner, Observer { follow ->
                    if (follow != null) {
                        adapter.setData(follow)
                        showLoading(false)
                        listFollower = follow
                    }
                })
            } else {
                followViewModel.setFollowing(username ?: "")
                showLoading(true)

                followViewModel.getFollowing().observe(viewLifecycleOwner, Observer { follow ->
                    if (follow != null) {
                        adapter.setData(follow)
                        showLoading(false)
                        listFollowing = follow
                    }
                })
            }
        }

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pb_follow.visibility = View.VISIBLE
        } else {
            pb_follow.visibility = View.GONE
        }
    }
}
