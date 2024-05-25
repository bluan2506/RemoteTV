package com.example.remotetv.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.connectsdk.sampler.fragment.AppFragment
import com.connectsdk.sampler.fragment.CastFragment
import com.connectsdk.sampler.fragment.RemoteFragment
import com.connectsdk.sampler.fragment.SettingFragment

class HomeAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val fragmentList = listOf(
        RemoteFragment(),
        AppFragment(),
        CastFragment(),
        SettingFragment(),
    )

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}