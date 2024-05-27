package com.connectsdk.sampler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.connectsdk.device.ConnectableDevice
import com.connectsdk.sampler.databinding.ItemDeviceBinding

class SearchAdapter (private var context: Context, private val deviceList: ArrayList<ConnectableDevice>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtName.text = deviceList[position].friendlyName
        holder.binding.txtIP.text = deviceList[position].lastKnownIPAddress
    }

    fun insertDevice(item: ConnectableDevice) {
        deviceList.add(item)
        notifyItemInserted(deviceList.size - 1)
    }

    class ViewHolder(val binding: ItemDeviceBinding) : RecyclerView.ViewHolder(binding.root)
}