package com.arya.consumerapp

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var cursor: Cursor? = null

    fun setData(cursor: Cursor) {
        this.cursor = cursor
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_user,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.FavoriteViewHolder, position: Int) {
        cursor?.moveToPosition(position)?.let { holder.bind(it) }
    }


    inner class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(moveToPosition: Boolean) {
            if (moveToPosition) {
                with(itemView) {
                    val username = findViewById<TextView>(R.id.tv_username)
                    username.text = cursor?.getString(cursor?.getColumnIndexOrThrow(Utility.COLUMN_LOGIN) ?: 0)

                    val avatarUrl = findViewById<CircleImageView>(R.id.img_avatar)
                    Glide.with(itemView)
                        .load(cursor?.getString(cursor?.getColumnIndexOrThrow(Utility.COLUMN_AVATAR_URL) ?: 0))
                        .apply(RequestOptions().override(50, 50))
                        .into(avatarUrl)
                }
            }
        }
    }

}