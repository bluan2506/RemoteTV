package com.connectsdk.sampler.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.connectsdk.sampler.R
import com.connectsdk.sampler.databinding.FragmentRemoteBinding
import com.percas.studio.template.ViewControl.gone
import com.percas.studio.template.ViewControl.invisible
import com.percas.studio.template.ViewControl.visible

class RemoteFragment : Fragment() {

    private lateinit var binding: FragmentRemoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRemoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTab1.setOnClickListener {
            binding.img1.setImageResource(R.drawable.bg_tab1)
            binding.topTab1.visible()
            binding.topTab2.invisible()
            binding.topTab3.invisible()
        }
        binding.btnTab2.setOnClickListener {
            binding.img1.setImageResource(R.drawable.bg_tab2)
            binding.topTab1.invisible()
            binding.topTab2.visible()
            binding.topTab3.invisible()
        }
        binding.btnTab3.setOnClickListener {
            binding.img1.setImageResource(R.drawable.bg_tab3)
            binding.topTab1.invisible()
            binding.topTab2.invisible()
            binding.topTab3.visible()
        }
    }
}