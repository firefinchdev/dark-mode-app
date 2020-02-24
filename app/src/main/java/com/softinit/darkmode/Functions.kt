package com.softinit.darkmode

import android.content.Context
import com.facebook.ads.Ad
import com.facebook.ads.AdSize
import com.google.firebase.analytics.FirebaseAnalytics


fun newAppInterstitialAdConditional(
    context: Context?,
    placementId: String,
    firebaseAnalytics: FirebaseAnalytics? = null,
    eventName: String = "",
    showOnLoad: Boolean = false,
    showInterval: Int = 1,
    destroyOnDismiss: Boolean = false
): AppInterstitialAd? {
    return AppInterstitialAd(
        context,
        placementId,
        firebaseAnalytics,
        eventName,
        showOnLoad,
        showInterval
    ).also {
        if (destroyOnDismiss) {
            it.addListener(object : EmptyInterstitialAdListener() {
                override fun onInterstitialDismissed(p0: Ad?) {
                    it.destroy()
                }
            })
        }
    }
}

fun newAppAdViewConditional(
    context: Context?,
    placementId: String,
    adSize: AdSize,
    firebaseAnalytics: FirebaseAnalytics? = null,
    eventName: String = ""
): AppAdView? {
    return AppAdView(
        context,
        placementId,
        adSize,
        firebaseAnalytics,
        eventName
    )
}