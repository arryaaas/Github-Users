package com.arya.apigithubusers.fragment


import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arya.apigithubusers.R
import com.arya.apigithubusers.adapter.UserAdapter
import com.arya.apigithubusers.model.UserItems
import com.arya.apigithubusers.activity.DetailActivity
import com.arya.apigithubusers.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user.*

/**
 * A simple [Fragment] subclass.
 */
class UserFragment : Fragment() {

    private lateinit var adapter: UserAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter =
            UserAdapter { showUserDetail(it) }
        adapter.notifyDataSetChanged()

        rv_user.adapter = adapter

        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            UserViewModel::class.java)

        userViewModel.setUser()
        showLoading(true)

        userViewModel.getUser().observe(viewLifecycleOwner, Observer { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
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
            pb_user.visibility = View.VISIBLE
        } else {
            pb_user.visibility = View.GONE
        }
    }
}
