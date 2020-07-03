package com.arya.apigithubusers.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arya.apigithubusers.BaseActivity
import com.arya.apigithubusers.local.FavDb
import com.arya.apigithubusers.model.Follow
import com.arya.apigithubusers.R
import com.arya.apigithubusers.adapter.SectionsPagerAdapter
import com.arya.apigithubusers.model.UserItems
import com.arya.apigithubusers.fragment.FollowFragment
import com.arya.apigithubusers.toast
import com.arya.apigithubusers.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.launch

const val ACTIVITY = "extra_activity"

class DetailActivity : BaseActivity() {

    private lateinit var detailViewModel: DetailViewModel
    var id = 0
    var username = ""

    companion object {
        lateinit var activity: String

        var listFollowerNew = listOf<Follow>()
        var listFollowingNew = listOf<Follow>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)
        collapsingToolbarLayout.isTitleEnabled = false

        setSupportActionBar(findViewById(R.id.toolbar_detail))
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "Detail User"
        }

        activity = intent.extras?.getString(ACTIVITY, "") ?: ""
        val extras = intent.getParcelableExtra(UserItems::class.java.simpleName) ?: UserItems()

        id = extras.id
        username = extras.login.toString()

        launch {
            this@DetailActivity.let {
                val isFavorite = FavDb(it).favDao().isFavoriteUser(id)
                if (isFavorite == 1) {
                    fab.setImageResource(R.drawable.ic_favorite_white_24dp)
                } else {
                    fab.setImageResource(R.drawable.ic_favorite_border_white_24dp)
                }
            }
        }

        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                supportFragmentManager
            )
        sectionsPagerAdapter.username = username
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        if (activity == "Favorite") {
            setUi(extras)
        } else {
            detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                DetailViewModel::class.java)

            detailViewModel.setDetail(username)
            showLoading(true)

            detailViewModel.getDetail().observe(this, Observer { userItems ->
                if (userItems != null) {
                    setUi(userItems)
                    showLoading(false)
                }
            })
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

    private fun setUi(userItems: UserItems) {

        tv_username.text = userItems.login
        userItems.avatarUrl.let {
            Glide.with(this@DetailActivity)
                .load(it)
                .into(img_avatar)
        }
        tv_name.text = userItems.name
        tv_repository.text = userItems.repository
        if (userItems.company == "null") {
            tv_company.text = "--"
        } else {
            tv_company.text = userItems.company
        }
        if (userItems.location == "null") {
            tv_location.text = "--"
        } else {
            tv_location.text = userItems.location
        }

        listFollowerNew = userItems.follower?.toList() ?: emptyList()
        listFollowingNew = userItems.following?.toList() ?: emptyList()

        fab.setOnClickListener{

            val fav = UserItems()
                fav.id = userItems.id
                fav.login = userItems.login
                fav.avatarUrl = userItems.avatarUrl
                fav.name = userItems.name
                fav.repository = userItems.repository
                fav.company = userItems.company
                fav.location = userItems.location
                fav.follower = FollowFragment.listFollower
                fav.following = FollowFragment.listFollowing

            launch {
                this@DetailActivity.let {
                    val isFavorite = FavDb(it).favDao().isFavoriteUser(id)
                    if (isFavorite != 1) {
                        FavDb(it).favDao().saveUser(fav)
                        fab.setImageResource(R.drawable.ic_favorite_white_24dp)
                        it.toast("Added To Favorite")
                    } else {
                        FavDb(it).favDao().delUser(fav)
                        fab.setImageResource(R.drawable.ic_favorite_border_white_24dp)
                        it.toast("Removed From Favorite")
                    }
                }
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            pb_detail.visibility = View.VISIBLE
            img_avatar.visibility = View.GONE
            tv_name.visibility = View.GONE
            tv_username.visibility = View.GONE
            tv_company.visibility = View.GONE
            tv_location.visibility = View.GONE
            tv_repository.visibility = View.GONE
        } else {
            pb_detail.visibility = View.GONE
            img_avatar.visibility = View.VISIBLE
            tv_name.visibility = View.VISIBLE
            tv_username.visibility = View.VISIBLE
            tv_company.visibility = View.VISIBLE
            tv_location.visibility = View.VISIBLE
            tv_repository.visibility = View.VISIBLE
        }
    }
}
