package com.arya.apigithubusers.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arya.apigithubusers.model.Follow
import com.arya.apigithubusers.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView

class FollowAdapter(
    private val onUserClick: (follow: Follow) -> Unit
): RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {

    private val mData = ArrayList<Follow>()

    fun setData(userItems: ArrayList<Follow>) {
        mData.clear()
        mData.addAll(userItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):FollowViewHolder {
        return FollowViewHolder(
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

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    inner class FollowViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(follow: Follow) {
            with(itemView) {
                val username = findViewById<TextView>(R.id.tv_username)
                username.text = follow.login

                val avatarUrl = findViewById<CircleImageView>(R.id.img_avatar)
                Glide.with(itemView)
                    .load(follow.avatarUrl)
                    .apply(RequestOptions().override(50, 50))
                    .into(avatarUrl)

                itemView.setOnClickListener {
                    onUserClick.invoke(follow)
                }
            }
        }
    }
}