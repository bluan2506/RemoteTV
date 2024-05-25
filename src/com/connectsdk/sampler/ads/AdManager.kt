package com.example.remotetv.ads

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdValue
import com.percas.studio.template.admob.AdmobManager
import com.percas.studio.template.model.InterAdHolder
import com.percas.studio.template.model.NativeAdHolder
import com.percas.studio.template.ViewControl.gone
import com.percas.studio.template.ViewControl.invisible
import com.percas.studio.template.ViewControl.visible
import java.util.Currency


object AdManager {

    val onResume = ""
    val AOA = ""

    val native = ""
    val inter = ""
    val interImage = ""
    val banner = ""
    val bannerCollap = ""

    var timeDelayInter = 10000L
    var currentTime = 0L
    var currentTimeImage = 0L

    var checkInterLoaded = false
    var checkInterImageLoaded = false


    fun loadNativeAd(context: Context, nativeAdHolder: NativeAdHolder) {
        AdmobManager.loadNativeAd(context, nativeAdHolder, object : AdmobManager.LoadAdCallBack {
            override fun onAdLoaded() {

            }

            override fun onAdFailed(error: String) {

            }

            override fun onAdClicked() {

            }

            override fun onAdPaid(adValue: AdValue, adUnit: String, mediationNetwork: String) {
                postRevenue(adUnit,adValue,"Load NativeAd",mediationNetwork,context::class.java.name)
            }

        })
    }

    fun showNativeAd(
        activity: Activity,
        nativeAdHolder: NativeAdHolder,
        viewNativeAd: ViewGroup,
        layoutNativeFormat: Int,
        isNativeAdMedium: Boolean
    ) {
        AdmobManager.showNativeAd(
            activity,
            nativeAdHolder,
            viewNativeAd,
            layoutNativeFormat,
            isNativeAdMedium,
            object : AdmobManager.ShowAdCallBack {
                override fun onAdShowed() {
                }

                override fun onAdFailed(error: String) {
                    viewNativeAd.gone()
                }

                override fun onAdClosed() {

                }

                override fun onAdPaid(adValue: AdValue, adUnit: String, mediationNetwork: String) {
                    postRevenue(adUnit,adValue,"Show NativeAd",mediationNetwork,activity::class.java.name)
                }

            })
    }

    fun loadAndShowNativeAds(
        activity: Activity,
        nativeAdHolder: NativeAdHolder,
        viewNativeAd: ViewGroup,
        layoutNativeAdFormat: Int,
        isNativeAdMedium: Boolean
    ) {
        AdmobManager.loadAndShowNativeAd(activity,
            nativeAdHolder,
            viewNativeAd,
            layoutNativeAdFormat,
            isNativeAdMedium,
            object : AdmobManager.LoadAndShowAdCallBack {
                override fun onAdLoaded() {

                }

                override fun onAdShowed() {

                }

                override fun onAdFailed(error: String) {
                    viewNativeAd.gone()
                }

                override fun onAdClosed() {

                }

                override fun onAdClicked() {

                }

                override fun onAdPaid(adValue: AdValue, adUnit: String, mediationNetwork: String) {
                    postRevenue(adUnit,adValue,"Load And Show NativeAd",mediationNetwork,activity::class.java.name)
                }

            })
    }

    private fun loadAndShowBanner(
        activity: Activity,
        idBannerAd: String,
        viewBannerAd: ViewGroup,
        viewLine: View
    ) {
        AdmobManager.loadAndShowBannerAd(
            activity,
            idBannerAd,
            viewBannerAd,
            object : AdmobManager.LoadAndShowAdCallBack {
                override fun onAdLoaded() {}

                override fun onAdShowed() {
                    viewBannerAd.visible()
                    viewLine.visible()
                }

                override fun onAdFailed(error: String) {
                    viewBannerAd.invisible()
                }

                override fun onAdClosed() {}

                override fun onAdClicked() {}

                override fun onAdPaid(adValue: AdValue, adUnit: String, mediationNetwork: String) {
                    postRevenue(adUnit,adValue,"BannerAd",mediationNetwork,activity::class.java.name)
                }

            })
    }

    private fun loadAndShowBannerCollapsibleAd(
        activity: Activity,
        idBannerCollapsible: String,
        isBottomCollapsible: Boolean,
        viewBannerCollapsibleAd: ViewGroup,
        viewLine: View
    ) {
        AdmobManager.loadAndShowBannerCollapsibleAd(
            activity,
            idBannerCollapsible,
            isBottomCollapsible,
            viewBannerCollapsibleAd,
            object : AdmobManager.LoadAndShowAdCallBack {
                override fun onAdLoaded() {
                }

                override fun onAdShowed() {

                }

                override fun onAdFailed(error: String) {
                    viewBannerCollapsibleAd.invisible()
                }

                override fun onAdClosed() {

                }

                override fun onAdClicked() {

                }

                override fun onAdPaid(adValue: AdValue, adUnit: String, mediationNetwork: String) {
                    postRevenue(adUnit,adValue,"BannerCollapsibleAd",mediationNetwork,activity::class.java.name)
                }

            })
    }

    fun loadInterstitialAd(context: Context, interAdHolder: InterAdHolder) {
        AdmobManager.loadInterstitialAd(
            context,
            interAdHolder,
            object : AdmobManager.LoadAdCallBack {
                override fun onAdLoaded() {
                }

                override fun onAdFailed(error: String) {
                }

                override fun onAdClicked() {
                }

                override fun onAdPaid(adValue: AdValue, adUnit: String, mediationNetwork: String) {
                    postRevenue(adUnit,adValue,"Load Interstitial",mediationNetwork,context::class.java.name)
                }
            })
    }

    fun showInterstitialAd(
        activity: Activity,
        interAdHolder: InterAdHolder,
        onAdClosed: () -> Unit
    ) {

        AdmobManager.showInterstitialAd(
            activity,
            interAdHolder,
            object : AdmobManager.ShowAdCallBack {
                override fun onAdShowed() {

                }

                override fun onAdFailed(error: String) {
                    loadInterstitialAd(activity, interAdHolder)
                    onAdClosed.invoke()
                }

                override fun onAdClosed() {
                    currentTime = System.currentTimeMillis()
                    loadInterstitialAd(activity, interAdHolder)
                    onAdClosed.invoke()
                }

                override fun onAdPaid(adValue: AdValue, adUnit: String, mediationNetwork: String) {
                    postRevenue(adUnit,adValue,"Show Interstitial",mediationNetwork,activity::class.java.name)
                }

            })
    }

    fun loadAndShowInterstitialAd(
        activity: Activity,
        interAdHolder: InterAdHolder,
        onAdClosed: () -> Unit
    ) {

        if (System.currentTimeMillis() - currentTime <= timeDelayInter) {
            onAdClosed.invoke()
            return
        }
        AdmobManager.loadAndShowInterstitialAd(
            activity,
            interAdHolder,
            object : AdmobManager.LoadAndShowAdCallBack {
                override fun onAdLoaded() {

                }

                override fun onAdShowed() {

                }

                override fun onAdFailed(error: String) {
                    onAdClosed.invoke()
                }

                override fun onAdClosed() {
                    currentTime = System.currentTimeMillis()
                    onAdClosed.invoke()
                }

                override fun onAdClicked() {

                }

                override fun onAdPaid(adValue: AdValue, adUnit: String, mediationNetwork: String) {
                }

            })
    }

    fun postRevenue(
        adUnit: String,
        adValue: AdValue,
        typeAd: String,
        mediation: String,
        position: String
    ) {
//        val customParams: MutableMap<String, String> = HashMap()
//        customParams[Scheme.COUNTRY] = adValue.currencyCode
//        customParams[Scheme.AD_UNIT] = adUnit
//        customParams[Scheme.AD_TYPE] = typeAd
//        customParams[Scheme.PLACEMENT] = position
//        customParams[Scheme.ECPM_PAYLOAD] = "encrypt"
//
//        val rev = adValue.valueMicros.toDouble() / 1000000
//        AppsFlyerAdRevenue.logAdRevenue(
//            mediation,
//            MediationNetwork.googleadmob,
//            Currency.getInstance(adValue.currencyCode),
//            rev,
//            customParams
//        )
    }
}