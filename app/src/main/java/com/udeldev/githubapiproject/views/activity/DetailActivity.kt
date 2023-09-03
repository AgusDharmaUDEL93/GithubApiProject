package com.udeldev.githubapiproject.views.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.udeldev.githubapiproject.R
import com.udeldev.githubapiproject.controllers.adapter.SectionsPagerAdapter
import com.udeldev.githubapiproject.controllers.view_models.DetailViewModel
import com.udeldev.githubapiproject.databinding.ActivityDetailBinding
import com.udeldev.githubapiproject.models.data.UserDetailModel

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "username"
        private val TAB_TITLES = intArrayOf(
            R.string.follower, R.string.following
        )
    }

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var activityDetailBinding: ActivityDetailBinding


    private fun initComponent() {
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponent()
        setContentView(activityDetailBinding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        username?.let { detailViewModel.gettingUserDetail(it) }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username.toString()
        activityDetailBinding.detailVp.adapter = sectionsPagerAdapter

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