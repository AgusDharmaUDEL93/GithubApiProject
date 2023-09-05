package com.udeldev.githubapiproject.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.udeldev.githubapiproject.controllers.adapter.UserListAdapter
import com.udeldev.githubapiproject.controllers.view_models.FavoriteViewModel
import com.udeldev.githubapiproject.databinding.ActivityFavoriteBinding
import com.udeldev.githubapiproject.helper.ViewModelFactory
import com.udeldev.githubapiproject.models.response.UserItemModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var activityFavoriteBinding: ActivityFavoriteBinding
    private lateinit var adapter: UserListAdapter
    private lateinit var favoriteList: List<UserItemModel>


    private fun initComponent() {
        activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        favoriteViewModel = obtainViewModel(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent()
        setContentView(activityFavoriteBinding.root)

        with(activityFavoriteBinding) {
            favoriteUserListRv.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = UserListAdapter()
            favoriteUserListRv.adapter
        }

        favoriteViewModel.gettingUserFavorite().observe(this) {
            val temp = mutableListOf<UserItemModel>()
            for (item in it) {
                temp.add(UserItemModel(item.username, item.avatarUrl))
            }
            favoriteList = temp
            setUserFavoriteList(favoriteList)
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    private fun setUserFavoriteList(userList: List<UserItemModel>) {
        adapter.setUserList(userList)
        activityFavoriteBinding.favoriteUserListRv.adapter = adapter
    }

}