package com.connectsdk.sampler.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.connectsdk.sampler.databinding.FragmentAppBinding

class AppFragment : Fragment() {
    private lateinit var binding: FragmentAppBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppBinding.inflate(inflater, container, false)
        return binding.root
    }
}