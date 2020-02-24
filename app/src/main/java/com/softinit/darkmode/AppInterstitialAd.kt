package com.softinit.darkmode

import android.content.Context
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.InterstitialAd
import com.facebook.ads.InterstitialAdListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.preference.PowerPreference
import com.preference.Preference

class AppInterstitialAd(
    context: Context?,
    placementId: String,
    private val firebaseAnalytics: FirebaseAnalytics? = null,
    private val eventName: String = "",
    showOnLoad: Boolean = false,
    private var showInterval: Int = 1
) : InterstitialAd(context, placementId), InterstitialAdListener {

    init {
        setAdListener(this)
        loadAd()
        if (showInterval < 1) showInterval = 1
    }

    private val preference: Preference = PowerPreference.getDefaultFile()
    private val intervalPrefKey = "AppInterstitialAd.$placementId"
    var onDismiss: ((Ad?) -> Unit)? = null

    private val adListeners = mutableListOf<InterstitialAdListener>()

    var showOnLoad: Boolean = showOnLoad
        set(value) {
            field = value
            if (field && isAdLoaded && !shownOnce) {
                showIfValidLoaded()
            }
        }

    private var shownOnce = false

    override fun onInterstitialDisplayed(ad: Ad?) {
        adListeners.forEach { it.onInterstitialDisplayed(ad) }
        firebaseAnalytics?.logEvent(eventName + "OnDisplayed")
    }

    override fun onAdClicked(ad: Ad?) {
        adListeners.forEach { it.onAdClicked(ad) }
        firebaseAnalytics?.logEvent(eventName + "OnAdClicked")
    }

    override fun onInterstitialDismissed(ad: Ad?) {
        onDismiss?.invoke(ad)
        adListeners.forEach { it.onInterstitialDismissed(ad) }
        firebaseAnalytics?.logEvent(eventName + "OnDismissed")
    }

    override fun onError(ad: Ad?, adError: AdError?) {
        adListeners.forEach { it.onError(ad, adError) }
        firebaseAnalytics?.logEvent(eventName + "OnError")
    }

    override fun onAdLoaded(ad: Ad?) {
        adListeners.forEach { it.onAdLoaded(ad) }
        firebaseAnalytics?.logEvent(eventName + "OnAdLoaded")
        if (showOnLoad && !shownOnce) {
            showIfValidLoaded()
        }
    }

    override fun onLoggingImpression(ad: Ad?) {
        adListeners.forEach { it.onLoggingImpression(ad) }
        firebaseAnalytics?.logEvent(eventName + "OnLoggingImpression")
    }

    fun addListener(listener: InterstitialAdListener) {
        adListeners.add(listener)
    }

    fun removeListener(listener: InterstitialAdListener) {
        adListeners.remove(listener)
    }

    override fun show(): Boolean {
        return super.show().also {
            if (it) {
                shownOnce = true
                firebaseAnalytics?.logEvent(eventName + "_show")
            }
        }
    }

    override fun show(interstitialShowAdConfig: InterstitialShowAdConfig?): Boolean {
        return super.show(interstitialShowAdConfig).also {
            if (it) {
                shownOnce = true
                firebaseAnalytics?.logEvent(eventName + "_show")
            }
        }
    }

    fun showIfValidLoadedInterval(): Boolean {
        var count = preference.getInt(intervalPrefKey, 0)
        count = (count + 1) % showInterval
        return if (count == 0) {
            if (showIfValidLoaded()) {
                preference.putInt(intervalPrefKey, count)
                true
            } else {
                false
            }
        } else {
            preference.putInt(intervalPrefKey, count)
            false
        }
    }

    fun showIfValidLoaded(): Boolean {
        if (!isAdLoaded) return false
        if (isAdInvalidated) return false
        return show()
    }
}