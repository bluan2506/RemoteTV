package com.connectsdk.sampler.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.connectsdk.sampler.BuildConfig
import com.connectsdk.sampler.R
import com.connectsdk.sampler.databinding.ActivityMainBinding
import com.example.remotetv.adapter.HomeAdapter
import com.example.remotetv.base.BaseActivity
import com.percas.studio.template.ViewControl.gone
import com.percas.studio.template.ViewControl.invisible
import com.percas.studio.template.ViewControl.visible

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupBottomNav()

        binding.btnConnect.setOnClickListener {
            startActivity(Intent(this@MainActivity, ConnectActivity::class.java))
        }
    }

    private fun setupBottomNav() {
        binding.viewPager.adapter = HomeAdapter(this)
        binding.botNav.setOnItemSelectedListener {
            when (it) {
                1 -> binding.viewPager.currentItem = 1
                2 -> binding.viewPager.currentItem = 2
                3 -> binding.viewPager.currentItem = 3
                else -> binding.viewPager.currentItem = 0
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    1 -> {
                        binding.botNav.itemActiveIndex = 1
                        binding.tab.visible()
                        binding.tabSetting.invisible()
                        binding.txtTab.text = getString(R.string.app)
                    }
                    2 -> {
                        binding.botNav.itemActiveIndex = 2
                        binding.tab.visible()
                        binding.tabSetting.invisible()
                        binding.txtTab.text = getString(R.string.cast)
                    }
                    3 -> {
                        binding.botNav.itemActiveIndex = 3
                        binding.tab.invisible()
                        binding.tabSetting.visible()
                        binding.txtVersion.setText(getString(R.string.version) + " " + BuildConfig.VERSION_NAME)
                    }
                    else -> {
                        binding.txtTab.text = getString(R.string.tv_remote)
                        binding.botNav.itemActiveIndex = 0
                        binding.tab.visible()
                        binding.tabSetting.invisible()
                    }
                }
                super.onPageSelected(position)
            }
        })
    }
}