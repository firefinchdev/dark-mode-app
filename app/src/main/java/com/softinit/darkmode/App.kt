package com.softinit.darkmode

import android.app.Application
import android.content.res.Resources
import com.facebook.ads.AudienceNetworkAds
import java.io.File


class App: Application(){
    companion object {
        @JvmStatic
        private lateinit var mInstance: App

        @JvmStatic
        private lateinit var res: Resources

        @JvmStatic
        fun getInstance(): App {
            return mInstance
        }

        @JvmStatic
        fun getAppResources(): Resources {
            return res
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (AudienceNetworkAds.isInAdsProcess(this)) {
            return
        }
        mInstance = this
        res = resources
        AudienceNetworkAds.initialize(this)
    }

}