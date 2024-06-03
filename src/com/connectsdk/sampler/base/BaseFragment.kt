package com.connectsdk.sampler.base

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import androidx.fragment.app.Fragment
import com.connectsdk.device.ConnectableDevice
import com.connectsdk.sampler.MainApplication.mTV
import com.connectsdk.service.capability.ExternalInputControl
import com.connectsdk.service.capability.KeyControl
import com.connectsdk.service.capability.Launcher
import com.connectsdk.service.capability.MediaControl
import com.connectsdk.service.capability.MediaPlayer
import com.connectsdk.service.capability.MouseControl
import com.connectsdk.service.capability.PowerControl
import com.connectsdk.service.capability.TVControl
import com.connectsdk.service.capability.TextInputControl
import com.connectsdk.service.capability.ToastControl
import com.connectsdk.service.capability.VolumeControl
import com.connectsdk.service.capability.WebAppLauncher

open class BaseFragment : Fragment {
    var launcher: Launcher? = null
        private set
    var mediaPlayer: MediaPlayer? = null
        private set
    var mediaControl: MediaControl? = null
        private set
    var tVControl: TVControl? = null
        private set
    var volumeControl: VolumeControl? = null
        private set
    var toastControl: ToastControl? = null
        private set
    var mouseControl: MouseControl? = null
        private set
    var textInputControl: TextInputControl? = null
        private set
    var powerControl: PowerControl? = null
        private set
    var externalInputControl: ExternalInputControl? = null
        private set
    var keyControl: KeyControl? = null
        private set
    var webAppLauncher: WebAppLauncher? = null
        private set
    var buttons: Array<Button>? = null
    var mContext: Context? = null

    constructor()

    constructor(context: Context?) {
        mContext = context
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tv = mTV
    }

    fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }

    fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }

    var tv: ConnectableDevice?
        get() = mTV
        set(tv) {
            mTV = tv

            if (tv == null) {
                launcher = null
                mediaPlayer = null
                mediaControl = null
                tVControl = null
                volumeControl = null
                toastControl = null
                textInputControl = null
                mouseControl = null
                externalInputControl = null
                powerControl = null
                keyControl = null
                webAppLauncher = null

            } else {
                launcher = mTV!!.getCapability(Launcher::class.java)
                mediaPlayer = mTV!!.getCapability(MediaPlayer::class.java)
                mediaControl = mTV!!.getCapability(MediaControl::class.java)
                tVControl = mTV!!.getCapability(TVControl::class.java)
                volumeControl = mTV!!.getCapability(VolumeControl::class.java)
                toastControl = mTV!!.getCapability(ToastControl::class.java)
                textInputControl = mTV!!.getCapability(TextInputControl::class.java)
                mouseControl = mTV!!.getCapability(MouseControl::class.java)
                externalInputControl = mTV!!.getCapability(ExternalInputControl::class.java)
                powerControl = mTV!!.getCapability(PowerControl::class.java)
                keyControl = mTV!!.getCapability(KeyControl::class.java)
                webAppLauncher = mTV!!.getCapability(WebAppLauncher::class.java)

            }
        }

    override fun getContext(): Context? {
        return mContext
    }

    protected fun disableButton(button: Button) {
        button.isEnabled = false
    }
}