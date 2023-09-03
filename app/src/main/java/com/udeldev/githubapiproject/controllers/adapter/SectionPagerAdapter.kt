package com.udeldev.githubapiproject.controllers.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.udeldev.githubapiproject.views.fragment.FollowFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username : String = ""
    override fun getItemCount(): Int = 2


    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.EXTRA_POSITION, position )
            putString(FollowFragment.EXTRA_USERNAME, username)
        }
        return fragment

    }

}