package com.example.githubuserlistv2.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuserlistv2.ui.detail.FollowerFragment
import com.example.githubuserlistv2.ui.detail.FollowingFragment

class PagerAdapter(activity: AppCompatActivity, var username: String) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment(username)
            1 -> fragment = FollowingFragment(username)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int = 2
}