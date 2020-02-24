package com.softinit.darkmode

import android.content.Context
import android.util.Log
import com.facebook.ads.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.preference.PowerPreference
import com.preference.Preference

class AppAdView(
    context: Context?,
    placementId: String,
    adSize: AdSize,
    private val firebaseAnalytics: FirebaseAnalytics? = null,
    private val eventName: String = ""
) : AdView(context, placementId, adSize), AdListener {

    init {
        setAdListener(this)
        loadAd()
    }

    private val preference: Preference = PowerPreference.getDefaultFile()
    private val intervalPrefKey = "AppInterstitialAd.$placementId"

    private val adListeners = mutableListOf<InterstitialAdListener>()

    override fun onAdClicked(ad: Ad?) {
        adListeners.forEach { it.onAdClicked(ad) }
        firebaseAnalytics?.logEvent(eventName + "_onAdClicked")
    }

    override fun onError(ad: Ad?, adError: AdError?) {
        adListeners.forEach { it.onError(ad, adError) }
        Log.d("FA", adError?.errorMessage)
        firebaseAnalytics?.logEvent(eventName + "_onError")
    }

    override fun onAdLoaded(ad: Ad?) {
        adListeners.forEach { it.onAdLoaded(ad) }
        firebaseAnalytics?.logEvent(eventName + "_onAdLoaded")
    }

    override fun onLoggingImpression(ad: Ad?) {
        adListeners.forEach { it.onLoggingImpression(ad) }
        firebaseAnalytics?.logEvent(eventName + "_onLogImpression")
    }

    fun addListener(listener: InterstitialAdListener) {
        adListeners.add(listener)
    }

    fun removeListener(listener: InterstitialAdListener) {
        adListeners.remove(listener)
    }
}