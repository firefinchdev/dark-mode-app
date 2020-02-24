package com.softinit.darkmode

import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.InterstitialAdListener

open class EmptyInterstitialAdListener: InterstitialAdListener {
    override fun onInterstitialDisplayed(p0: Ad?) {
        
    }

    override fun onAdClicked(p0: Ad?) {
        
    }

    override fun onInterstitialDismissed(p0: Ad?) {
        
    }

    override fun onError(p0: Ad?, p1: AdError?) {
        
    }

    override fun onAdLoaded(p0: Ad?) {
        
    }

    override fun onLoggingImpression(p0: Ad?) {
        
    }
}