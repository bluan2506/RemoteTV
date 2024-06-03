package com.connectsdk.sampler.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.connectsdk.device.ConnectableDevice
import com.connectsdk.device.ConnectableDeviceListener
import com.connectsdk.device.DevicePicker
import com.connectsdk.discovery.DiscoveryManager
import com.connectsdk.discovery.DiscoveryManagerListener
import com.connectsdk.sampler.MainActivity
import com.connectsdk.sampler.MainApplication.mTV
import com.connectsdk.sampler.R
import com.connectsdk.sampler.adapter.SearchAdapter
import com.connectsdk.sampler.databinding.ActivityConnectBinding
import com.connectsdk.service.DeviceService
import com.connectsdk.service.DeviceService.PairingType
import com.connectsdk.service.capability.KeyControl
import com.connectsdk.service.command.ServiceCommandError
import com.example.remotetv.base.BaseActivity
import com.percas.studio.template.ViewControl.gone
import com.percas.studio.template.ViewControl.visible


class ConnectActivity : BaseActivity() {

    private lateinit var binding: ActivityConnectBinding
    private lateinit var discoveryManager: DiscoveryManager
    private val deviceList: ArrayList<ConnectableDevice> = ArrayList()
    private lateinit var adapter: SearchAdapter
    private lateinit var pairingAlertDialog: AlertDialog
    private lateinit var pairingCodeDialog: AlertDialog
    private lateinit var devicePicker: DevicePicker
    private var deviceListener = object: ConnectableDeviceListener {
        override fun onDeviceReady(device: ConnectableDevice?) {
            Log.d("2ndScreenAPP", "onPairingSuccess")
            if (pairingAlertDialog.isShowing) {
                pairingAlertDialog.dismiss()
            }
            if (pairingCodeDialog.isShowing) {
                pairingCodeDialog.dismiss()
            }
            finish()
        }

        override fun onDeviceDisconnected(device: ConnectableDevice?) {
        }

        override fun onPairingRequired(
            device: ConnectableDevice?,
            service: DeviceService?,
            pairingType: PairingType?
        ) {
            Log.d("2ndScreenAPP", "Connected to " + mTV.ipAddress)

            when (pairingType) {
                PairingType.FIRST_SCREEN -> {
                    Log.d("2ndScreenAPP", "First Screen")
                    pairingAlertDialog.show()
                }

                PairingType.PIN_CODE, PairingType.MIXED -> {
                    Log.d("2ndScreenAPP", "Pin Code")
                    pairingCodeDialog.show()
                }

                PairingType.NONE -> {}
                else -> {}
            }
        }

        override fun onCapabilityUpdated(
            device: ConnectableDevice?,
            added: MutableList<String>?,
            removed: MutableList<String>?
        ) {

        }

        override fun onConnectionFailed(device: ConnectableDevice?, error: ServiceCommandError?) {
            Log.d("2ndScreenAPP", "onConnectFailed")
            connectFailed(mTV)
        }

    }

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

        setupPicker()
        devicePicker = DevicePicker(this)

        adapter = SearchAdapter(this, deviceList) {
            mTV = it
            mTV.addListener(deviceListener)
            mTV.setPairingType(null)
            mTV.connect()
            devicePicker.pickDevice(mTV)
        }
        binding.rcvDevice.adapter = adapter

        discoveryManager = DiscoveryManager.getInstance()
        discoveryManager.start()
        discoveryManager.setCapabilityFilters()
        discoveryManager.addListener(object : DiscoveryManagerListener {
            override fun onDeviceAdded(manager: DiscoveryManager?, device: ConnectableDevice?) {
                binding.viewSearch.gone()
                binding.viewDevice.visible()
                device?.let {
//                    if (deviceList.none { it.friendlyName == device.friendlyName }) {
//                        deviceList.add(device)
                        adapter.insertDevice(device)
                        Toast.makeText(this@ConnectActivity, device.friendlyName + deviceList.size, Toast.LENGTH_SHORT).show()
//                    }
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

    private fun setupPicker() {
        pairingAlertDialog = AlertDialog.Builder(this)
            .setTitle("Pairing with TV")
            .setMessage("Please confirm the connection on your TV")
            .setPositiveButton("Okay", null)
            .setNegativeButton("Cancel") { dialog, which ->
                finish()
            }
            .create()

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT

        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        pairingCodeDialog = AlertDialog.Builder(this)
            .setTitle("Enter Pairing Code on TV")
            .setView(input)
            .setPositiveButton(android.R.string.ok) { arg0, arg1 ->
                if (mTV != null) {
                    val value = input.text.toString().trim { it <= ' ' }
                    mTV.sendPairingKey(value)
                    imm.hideSoftInputFromWindow(input.windowToken, 0)
                }
            }
            .setNegativeButton(
                android.R.string.cancel
            ) { dialog, whichButton ->
                finish()
                imm.hideSoftInputFromWindow(input.windowToken, 0)
            }
            .create()
    }

    override fun onDestroy() {
        super.onDestroy()
        discoveryManager.stop()
    }

    fun connectFailed(device: ConnectableDevice?) {
        if (device != null) Log.d("2ndScreenAPP", "Failed to connect to " + device.ipAddress)

        if (mTV != null) {
            mTV.removeListener(deviceListener)
            mTV.disconnect()
            mTV = null
        }
    }
}