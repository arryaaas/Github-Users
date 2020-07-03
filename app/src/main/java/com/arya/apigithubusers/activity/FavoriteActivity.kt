package com.arya.apigithubusers.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.arya.apigithubusers.BaseActivity
import com.arya.apigithubusers.local.FavDb
import com.arya.apigithubusers.R
import com.arya.apigithubusers.model.UserItems
import com.arya.apigithubusers.adapter.UserAdapter
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.launch

class FavoriteActivity : BaseActivity() {

    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        setSupportActionBar(findViewById(R.id.toolbar_favorite))
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "Favorite User"
        }

        updateUI()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        adapter =
            UserAdapter { showUserDetail(it) }
        adapter.notifyDataSetChanged()

        rv_favuser.adapter = adapter

        launch {
            this@FavoriteActivity.let {
                val userItems = FavDb(it).favDao().readUser()
                adapter.setData(userItems.toCollection(ArrayList()))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(this, SettingsActivity::class.java)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showUserDetail(it: UserItems) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(UserItems::class.java.simpleName, it)
            putExtra(ACTIVITY, "Favorite")
        }
        val transition = ActivityOptions.makeCustomAnimation(this, R.anim.enter_from_right, R.anim.exit_to_left)
        startActivity(intent, transition.toBundle())
    }
}
