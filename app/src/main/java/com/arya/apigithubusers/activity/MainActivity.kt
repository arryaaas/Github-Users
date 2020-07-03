package com.arya.apigithubusers.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.arya.apigithubusers.R
import com.arya.apigithubusers.fragment.SearchFragment
import com.arya.apigithubusers.fragment.UserFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar_main))
        supportActionBar?.let {
            it.title = "Github User's"
        }

        setSearchView()

        val mFragmentManager = supportFragmentManager
        val mUserFragment = UserFragment()
        val fragment = mFragmentManager.findFragmentByTag(UserFragment::class.java.simpleName)
        if (fragment !is UserFragment) {
            mFragmentManager
                .beginTransaction()
                .add(R.id.frame_container, mUserFragment, UserFragment::class.java.simpleName)
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_change_settings -> {
                val mIntent = Intent(this, SettingsActivity::class.java)
                startActivity(mIntent)
                true
            }
            R.id.action_show_favorite -> {
                val mIntent = Intent(this, FavoriteActivity::class.java)
                startActivity(mIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setSearchView() {
        searchview.queryHint = resources.getString(R.string.search_username)

        searchview.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(username: String): Boolean {
                val mFragmentManager = supportFragmentManager
                val mSearchFragment =
                    SearchFragment()

                val mBundle = Bundle()
                mBundle.putString(SearchFragment.EXTRA_USERNAME, username)

                mSearchFragment.arguments = mBundle

                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, mSearchFragment, SearchFragment::class.java.simpleName)
                    if (mFragmentManager.backStackEntryCount < 1)
                        addToBackStack(null)
                }.commit()

                searchview.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        searchview.setOnCloseListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                onBackPressed()
            }
            false
        }
    }
}
