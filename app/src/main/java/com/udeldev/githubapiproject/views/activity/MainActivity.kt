package com.udeldev.githubapiproject.views.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.udeldev.githubapiproject.R
import com.udeldev.githubapiproject.controllers.adapter.UserListAdapter
import com.udeldev.githubapiproject.controllers.view_models.MainViewModel
import com.udeldev.githubapiproject.controllers.view_models.SettingViewModel
import com.udeldev.githubapiproject.databinding.ActivityMainBinding
import com.udeldev.githubapiproject.helper.SettingPreferences
import com.udeldev.githubapiproject.helper.ViewModelFactorySettingPreferences
import com.udeldev.githubapiproject.helper.dataStore
import com.udeldev.githubapiproject.models.response.UserItemModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var adapter: UserListAdapter

    private fun initComponent() {
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent()
        setContentView(activityMainBinding.root)

        with(activityMainBinding) {

            userListRv.layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = UserListAdapter()
            userListRv.adapter = adapter

            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { textView, actionId, event ->
                searchBar.text = searchView.text
                searchView.hide()
                if (searchView.text?.isEmpty() == true) {
                    mainViewModel.gettingUser()
                } else {
                    mainViewModel.gettingSearchUser(searchView.text.toString())
                }
                false
            }
            searchBar.inflateMenu(R.menu.favorite_menu)
            searchBar.setOnMenuItemClickListener {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }

            searchBar.inflateMenu(R.menu.setting_menu)
            searchBar.setOnMenuItemClickListener {
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
                true
            }
        }

        val pref = SettingPreferences.getInstance(application.dataStore)

        val settingViewModel =
            ViewModelProvider(this, ViewModelFactorySettingPreferences(pref))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        mainViewModel.gettingUser()

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.userList.observe(this) {
            setUserListData(it)
        }
    }

    private fun setUserListData(userList: List<UserItemModel>) {
        adapter.setUserList(userList)
        activityMainBinding.userListRv.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        activityMainBinding.mainPB.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}