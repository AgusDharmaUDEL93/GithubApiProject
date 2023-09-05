package com.udeldev.githubapiproject.controllers.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.udeldev.githubapiproject.R
import com.udeldev.githubapiproject.models.response.UserItemModel
import com.udeldev.githubapiproject.views.activity.DetailActivity

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    private var _userList : List<UserItemModel?>? = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun  setUserList (value : List<UserItemModel>) {
        _userList = value
        notifyDataSetChanged()
    }
    inner class UserListViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val githubIdItem : TextView = itemView.findViewById(R.id.githubIdItemTv)
        val avatarItem : ImageView = itemView.findViewById(R.id.avatarItemIv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserListViewHolder(view)

    }

    override fun getItemCount(): Int = _userList?.size ?: 0

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.githubIdItem.text = _userList?.get(position)?.githubId ?: ""
        Glide.with(holder.itemView.context)
            .load(_userList?.get(position)?.avatarUrl ?: "https://i.stack.imgur.com/l60Hf.png")
            .into(holder.avatarItem)
        holder.itemView.setOnClickListener {
            val moveIntent = Intent(holder.itemView.context, DetailActivity::class.java)
            moveIntent.putExtra(DetailActivity.EXTRA_USERNAME, _userList?.get(position)?.githubId)
            holder.itemView.context.startActivity(moveIntent)
        }
    }
}