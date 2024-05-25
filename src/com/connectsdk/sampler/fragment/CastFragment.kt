package com.connectsdk.sampler.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.connectsdk.sampler.databinding.FragmentCastBinding

class CastFragment : Fragment() {
    private lateinit var binding: FragmentCastBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCastBinding.inflate(inflater, container, false)
        return binding.root
    }
}