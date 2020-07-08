package com.arya.consumerapp

import android.content.ContentResolver
import android.database.Cursor
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar_favorite))
        supportActionBar?.let {
            it.title = "Consumer App"
        }

        updateUI()
    }

    private fun updateUI() {
        adapter = FavoriteAdapter()
        adapter.notifyDataSetChanged()

        rv_favuser.adapter = adapter

        val contentResolver: ContentResolver = this.contentResolver
        val cursor: Cursor? = contentResolver.query(
            Utility.CONTENT_USER_URI,
            null,
            null,
            null,
            null
        )

        if (cursor != null && cursor.count > 0) {
            adapter.setData(cursor)
        } else {
            tv_oops.visibility = View.VISIBLE
            tv_no.visibility = View.VISIBLE
        }
    }
}
