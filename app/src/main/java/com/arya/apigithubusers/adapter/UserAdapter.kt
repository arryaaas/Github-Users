package com.arya.apigithubusers.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arya.apigithubusers.R
import com.arya.apigithubusers.model.UserItems
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(
    private val onUserClick: (userItems: UserItems) -> Unit
): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val mData = ArrayList<UserItems>()

    fun setData(userItems: ArrayList<UserItems>) {
        mData.clear()
        mData.addAll(userItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
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
        return mData.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    inner class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(userItems: UserItems) {
            with(itemView) {
                val username = findViewById<TextView>(R.id.tv_username)
                username.text = userItems.login

                val avatarUrl = findViewById<CircleImageView>(R.id.img_avatar)
                Glide.with(itemView)
                    .load(userItems.avatarUrl)
                    .apply(RequestOptions().override(50, 50))
                    .into(avatarUrl)

                itemView.setOnClickListener {
                    onUserClick.invoke(userItems)
                }
            }
        }
    }
}