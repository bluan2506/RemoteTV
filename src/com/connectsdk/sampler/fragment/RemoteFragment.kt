package com.connectsdk.sampler.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.connectsdk.sampler.databinding.FragmentRemoteBinding

class RemoteFragment : Fragment() {

    private lateinit var binding: FragmentRemoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRemoteBinding.inflate(inflater, container, false)
        return binding.root
    }
}