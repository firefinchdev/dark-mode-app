package com.softinit.darkmode

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_UNDEFINED
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.softinit.darkmode.Utils.openBrowser
import kotlinx.android.synthetic.main.activity_information.*
import java.lang.Exception


class InformationActivity : AppCompatActivity() {

    private var appInterstitialAd: AppInterstitialAd? = null
    val firebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)
        when(intent?.getIntExtra("type", UI_MODE_NIGHT_UNDEFINED)){
            UI_MODE_NIGHT_YES -> {
                tvTitle.text = getString(R.string.success)
                msgSuccess.visibility = View.VISIBLE
                msgError.visibility = View.GONE
                firebaseAnalytics.logEvent("InformationActivity:UI_MODE_NIGHT_YES")
            }
            UI_MODE_NIGHT_UNDEFINED -> {
                tvTitle.text = getString(R.string.error)
                msgSuccess.visibility = View.GONE
                msgError.visibility = View.VISIBLE
                firebaseAnalytics.logEvent("InformationActivity:UI_MODE_NIGHT_UNDEFINED")
            }
        }
        ic_back.setOnClickListener {
            onBackPressed()
        }
        btn2.setOnClickListener {
            openBrowser(this,"https://darkmode.maju.systems/en-US/faq.html")
        }
        Utils.setStatusBarIconsColor(this)
    }
    override fun onBackPressed() {
        appInterstitialAd?.showIfValidLoadedInterval()
        finish()
    }
}
