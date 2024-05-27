package com.connectsdk.sampler.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import com.connectsdk.device.ConnectableDevice
import com.connectsdk.discovery.DiscoveryManager
import com.connectsdk.discovery.DiscoveryManagerListener
import com.connectsdk.sampler.MainActivity
import com.connectsdk.sampler.R
import com.connectsdk.sampler.adapter.SearchAdapter
import com.connectsdk.sampler.databinding.ActivityConnectBinding
import com.connectsdk.service.command.ServiceCommandError
import com.example.remotetv.base.BaseActivity
import com.percas.studio.template.ViewControl.gone
import com.percas.studio.template.ViewControl.visible


class ConnectActivity : BaseActivity() {

    private lateinit var binding: ActivityConnectBinding
    private lateinit var discoveryManager: DiscoveryManager
    private val deviceList: ArrayList<ConnectableDevice> = ArrayList()
    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = SearchAdapter(this, deviceList)
        binding.rcvDevice.adapter = adapter

        discoveryManager = DiscoveryManager.getInstance()
        discoveryManager.start()
        discoveryManager.addListener(object : DiscoveryManagerListener {
            override fun onDeviceAdded(manager: DiscoveryManager?, device: ConnectableDevice?) {
                binding.viewSearch.gone()
                binding.viewDevice.visible()
                device?.let {
                    if (deviceList.none { it.friendlyName == device.friendlyName }) {
                        deviceList.add(device)
//                        adapter.insertDevice(device)
                        Toast.makeText(this@ConnectActivity, device.friendlyName, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onDeviceUpdated(manager: DiscoveryManager?, device: ConnectableDevice?) {
                if (deviceList.indexOf(device) >= 0) {
                    deviceList[deviceList.indexOf(device)] = device!!
                }
            }

            override fun onDeviceRemoved(manager: DiscoveryManager?, device: ConnectableDevice?) {
                deviceList.remove(device!!)
            }

            override fun onDiscoveryFailed(
                manager: DiscoveryManager?,
                error: ServiceCommandError?
            ) {
                Toast.makeText(this@ConnectActivity, "Fail to get device", Toast.LENGTH_SHORT)
                    .show()
            }

        })

        binding.btnInfo.setOnClickListener {
            startActivity(Intent(this@ConnectActivity, MainActivity::class.java))
        }

        binding.btnBack.setOnClickListener { finish() }

        Handler().postDelayed({
            if (deviceList.isEmpty()) {
                binding.viewSearch.gone()
                binding.viewDevice.visible()
            }
        },8000)
    }

    override fun onDestroy() {
        super.onDestroy()
        discoveryManager.stop()
    }
}