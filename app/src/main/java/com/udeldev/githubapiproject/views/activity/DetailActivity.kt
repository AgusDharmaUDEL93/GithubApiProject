package com.udeldev.githubapiproject.views.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.udeldev.githubapiproject.BuildConfig
import com.udeldev.githubapiproject.R
import com.udeldev.githubapiproject.controllers.adapter.SectionsPagerAdapter
import com.udeldev.githubapiproject.controllers.view_models.DetailViewModel
import com.udeldev.githubapiproject.databinding.ActivityDetailBinding
import com.udeldev.githubapiproject.helper.ViewModelFactory
import com.udeldev.githubapiproject.models.data.FavoriteUserModel
import com.udeldev.githubapiproject.models.response.UserDetailModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "username"
        private val TAB_TITLES = intArrayOf(
            R.string.follower, R.string.following
        )
    }

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var activityDetailBinding: ActivityDetailBinding
    private var favoriteUserByUsername: FavoriteUserModel? = null


    private fun initComponent() {
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        detailViewModel = obtainViewModel(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent()
        setContentView(activityDetailBinding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME) ?: return
        detailViewModel.gettingUserDetail(username)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        activityDetailBinding.detailVp.adapter = sectionsPagerAdapter

        detailViewModel.getFavoriteUserByUsername(username).observe(this) {
            favoriteUserByUsername = it
            Log.i("SetFavorite private", "$favoriteUserByUsername")
            if (favoriteUserByUsername == null) {
                activityDetailBinding.favoriteFAB.setImageResource(R.drawable.ic_favorite_border)
                return@observe
            }
            activityDetailBinding.favoriteFAB.setImageResource(R.drawable.ic_favorite)
        }

        activityDetailBinding.favoriteFAB.setOnClickListener {
            val favoriteUser = FavoriteUserModel(username, detailViewModel.userDetail.value?.avatarUrl)

            if (favoriteUserByUsername == null) {
                detailViewModel.insertFavoriteUser(favoriteUser)
                return@setOnClickListener
            }
            detailViewModel.deleteFavoriteUser(favoriteUser)
        }

        activityDetailBinding.shareFAB.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "https://github.com/${username}")
                type = "text/plain"
            }
            startActivity(sendIntent)
        }


        TabLayoutMediator(activityDetailBinding.detailTL, activityDetailBinding.detailVp) { tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.userDetail.observe(this) {
            setUserDetailData(it)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    private fun setUserDetailData(userDetail: UserDetailModel) {
        activityDetailBinding.nameDetailTv.text = userDetail.name
        activityDetailBinding.githubIdDetailTv.text = userDetail.login
        activityDetailBinding.sumFollowerTv.text = "${userDetail.followers} Followers"
        activityDetailBinding.sumFollowingTv.text = "${userDetail.following} Following"
        Glide.with(this).load(userDetail.avatarUrl).into(activityDetailBinding.avatarDetailIv)
    }


    private fun showLoading(isLoading: Boolean) {
        activityDetailBinding.detailPB.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}