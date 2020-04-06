package com.softinit.darkmode

import android.app.Application
import android.content.res.Resources
import com.google.android.gms.ads.MobileAds
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
        mInstance = this
        res = resources
        MobileAds.initialize(this)
    }

}