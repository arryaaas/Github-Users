package com.arya.apigithubusers.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.arya.apigithubusers.R
import com.arya.apigithubusers.local.FavDb
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

internal class StackRemoteViewsFactory(
    private val mContext: Context
): RemoteViewsService.RemoteViewsFactory {

    private val avatars: ArrayList<String> = ArrayList()

    override fun onCreate() {

    }

    override fun onDataSetChanged() {

        val identityToken = Binder.clearCallingIdentity()

        avatars.clear()

        val userItems = FavDb(mContext).favDao().readFav()

        for (i in userItems.indices) {
            val url: String = userItems[i].avatarUrl.toString()
            avatars.add(url)
        }

        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = avatars.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        try {
            val bitmap: Bitmap = Glide.with(mContext)
                .asBitmap()
                .load(avatars[position])
                .apply(RequestOptions())
                .submit()
                .get()

            rv.setImageViewBitmap(R.id.imageView, bitmap)
        } catch (e: Exception){
            e.printStackTrace()
        }

        val extras = bundleOf(
            ImageBannerWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}