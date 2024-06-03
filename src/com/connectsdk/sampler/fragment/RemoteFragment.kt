package com.connectsdk.sampler.fragment

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.connectsdk.sampler.MainApplication.mTV
import com.connectsdk.sampler.R
import com.connectsdk.sampler.base.BaseFragment
import com.connectsdk.sampler.base.Common
import com.connectsdk.sampler.databinding.FragmentRemoteBinding
import com.connectsdk.sampler.util.TestResponseObject
import com.connectsdk.service.capability.KeyControl
import com.connectsdk.service.capability.PowerControl
import com.connectsdk.service.capability.VolumeControl
import com.percas.studio.template.ViewControl.invisible
import com.percas.studio.template.ViewControl.visible

class RemoteFragment : BaseFragment(), View.OnClickListener {

    private lateinit var binding: FragmentRemoteBinding
    private lateinit var testResponse: TestResponseObject

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRemoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTab1.setOnClickListener(this)
        binding.btnTab2.setOnClickListener(this)
        binding.btnTab3.setOnClickListener(this)
        binding.btnHome.setOnClickListener(this)
        binding.btnOff.setOnClickListener(this)

    }

    private fun vibratePhone() {
        if (Common.getHaptic(requireContext())) {
            val vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(50)
            }
        }
    }

    override fun onClick(p0: View?) {
//        vibratePhone()
        when (p0) {
            binding.btnTab1 -> {
                binding.img1.setImageResource(R.drawable.bg_tab1)
                binding.topTab1.visible()
                binding.topTab2.invisible()
                binding.topTab3.invisible()
            }
            binding.btnTab2 -> {
                binding.img1.setImageResource(R.drawable.bg_tab2)
                binding.topTab1.invisible()
                binding.topTab2.visible()
                binding.topTab3.invisible()
            }
            binding.btnTab3 -> {
                binding.img1.setImageResource(R.drawable.bg_tab3)
                binding.topTab1.invisible()
                binding.topTab2.invisible()
                binding.topTab3.visible()
            }
            binding.btnHome -> {
                if (mTV.hasCapability(VolumeControl.Volume_Up_Down)) {
                    if (mTV.getCapability(VolumeControl::class.java) != null) {
                        mTV.getCapability(KeyControl::class.java).home(null)
                        testResponse = TestResponseObject(
                            true,
                            TestResponseObject.SuccessCode,
                            TestResponseObject.HomeClicked
                        )
                    }
                }
            }
            binding.btnOff -> {
                if (mTV.hasCapability(PowerControl.Off)) {
                    if (powerControl != null) {
                        powerControl?.powerOff(null)
                    }
                }
            }
        }
    }
}