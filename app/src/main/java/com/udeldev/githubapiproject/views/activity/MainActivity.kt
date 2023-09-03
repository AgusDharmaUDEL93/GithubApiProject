package com.udeldev.githubapiproject.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.udeldev.githubapiproject.controllers.adapter.UserListAdapter
import com.udeldev.githubapiproject.controllers.view_models.MainViewModel
import com.udeldev.githubapiproject.databinding.ActivityMainBinding
import com.udeldev.githubapiproject.models.data.UserItemModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var adapter: UserListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent()
        setContentView(activityMainBinding.root)

        with(activityMainBinding){

            userListRv.layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = UserListAdapter()
            userListRv.adapter = adapter

            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    if (searchView.text?.isEmpty() == true){
                        mainViewModel.gettingUser()
                    }else{
                        mainViewModel.gettingSearchUser(searchView.text.toString())
                    }
                    false
                }
        }

        mainViewModel.gettingUser()

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        mainViewModel.userList.observe(this){
            setUserListData(it)
        }


    }
    private fun setUserListData (userList : List<UserItemModel>){
        adapter.setUserList(userList)
        activityMainBinding.userListRv.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        activityMainBinding.mainPB.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun initComponent (){
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        mainViewModel = ViewModelProvider(this) [MainViewModel::class.java]
    }
}