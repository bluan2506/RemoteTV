package com.connectsdk.sampler.base

import android.content.Context
import android.content.SharedPreferences

object Common {
    private const val PREFS_NAME = "RemoteTVPrefs"
    fun setHaptic(context: Context, value: Boolean) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("KEY_HAPTIC", value)
        editor.apply()
    }

    fun getHaptic(context: Context): Boolean {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("KEY_HAPTIC", true)
    }
}