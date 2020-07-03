package com.arya.apigithubusers.fragment


import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arya.apigithubusers.R
import com.arya.apigithubusers.adapter.UserAdapter
import com.arya.apigithubusers.model.UserItems
import com.arya.apigithubusers.activity.DetailActivity
import com.arya.apigithubusers.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    private lateinit var adapter: UserAdapter
    private lateinit var searchViewModel: SearchViewModel

    companion object {
        var EXTRA_USERNAME = "extra_username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var username = ""
        if (arguments != null) {
            username = arguments?.getString(EXTRA_USERNAME).toString()
        }

        adapter =
            UserAdapter { showUserDetail(it) }
        adapter.notifyDataSetChanged()

        rv_search.adapter = adapter

        searchViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            SearchViewModel::class.java)

        searchViewModel.setSearch(username)
        showLoading(true)

        searchViewModel.getSearch().observe(viewLifecycleOwner, Observer { userItems ->
            showLoading(false)
            if (userItems.isNullOrEmpty()) {
                Toast.makeText(context?.applicationContext, "No result for username", Toast.LENGTH_LONG).show()
            } else {
                adapter.setData(userItems)
            }
        })
    }

    private fun showUserDetail(it: UserItems) {
        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra(UserItems::class.java.simpleName, it)
        }
        val transition = ActivityOptions.makeCustomAnimation(context, R.anim.enter_from_right, R.anim.exit_to_left)
        startActivity(intent, transition.toBundle())
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pb_search.visibility = View.VISIBLE
        } else {
            pb_search.visibility = View.GONE
        }
    }
}
