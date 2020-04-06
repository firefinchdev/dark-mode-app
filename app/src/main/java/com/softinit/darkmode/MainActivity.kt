package com.softinit.darkmode

import android.app.UiModeManager
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Configuration.*
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.softinit.darkmode.AppConstants.APP_RATE_DIALOG_INTERVAL
import com.softinit.darkmode.AppConstants.THEME_SWITCH_AD_INTERVAL
import com.softinit.darkmode.AppPreferences.firstStart
import com.softinit.darkmode.AppPreferences.isDarkThemeEnabled
import com.softinit.darkmode.AppPreferences.userSessionCount
import com.softinit.darkmode.Utils.isUsingNightModeResources
import com.softinit.darkmode.Utils.setStatusBarIconsColor
import kotlinx.android.synthetic.main.main_view.*


// onConfig changed wouldn't be called if you are try to set day mode on day mode and vice versa
class MainActivity : AppCompatActivity() {

    private var uiModeManager: UiModeManager?=null
    private var mInterstitialAd: InterstitialAd? = null
    val firebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        uiModeManager = getSystemService(UI_MODE_SERVICE) as UiModeManager
        initiateLayout()
        setupBannerAd()
        setupInterstitialAd()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun initiateLayout(){
//        ivFaq.setOnClickListener {
//            startActivity(Intent(this,FaqActivity::class.java))
//        }
        toggleSwitch.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if(isChecked)
            when(checkedId){
                R.id.btnDay -> {
                    showAd()
                    if(switchAutoMode.isChecked) switchAutoMode.isChecked = false
                    setNightModeBtn(isNight = false)
                    uiModeManager?.nightMode = UiModeManager.MODE_NIGHT_NO
                    showError()
                }
                R.id.btnNight -> {
                    showAd()
                    if(switchAutoMode.isChecked) switchAutoMode.isChecked = false
                    setNightModeBtn(isNight = true)
                    uiModeManager?.nightMode = UiModeManager.MODE_NIGHT_YES
                    showError()
                }
            }
        }
        switchAutoMode.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                showAd()
                uiModeManager?.nightMode = UiModeManager.MODE_NIGHT_AUTO
                toggleSwitch.clearChecked()
            }
            else{
                when(isUsingNightModeResources(this)){
                    NightMode.YES -> {
                        toggleSwitch.check(R.id.btnNight)
                        uiModeManager?.nightMode = UiModeManager.MODE_NIGHT_YES
                    }
                    NightMode.NO -> {
                        toggleSwitch.check(R.id.btnDay)
                        uiModeManager?.nightMode = UiModeManager.MODE_NIGHT_NO
                    }
                    NightMode.UNKNOWN -> showError()
                }
            }
//            showError()
        }
        msgLy.setOnClickListener {
            val intent = Intent(this,InformationActivity::class.java)
            when(msgTitle.text){
                getString(R.string.sw_info2),getString(R.string.sw_info3),getString(R.string.sw_info1) -> intent.putExtra("type",UI_MODE_NIGHT_YES)
                getString(R.string.sw_info4) -> intent.putExtra("type",UI_MODE_NIGHT_UNDEFINED)
            }
            startActivity(intent)
        }
        setInitialMode()
        setStatusBarIconsColor(this)
        if(firstStart) firstStart = false
        userSessionCount += 1
        AppRatingDialog.getDialog(this, true).show()
    }

    private fun setInitialMode(){
        when (getCurrentNightMode()) {
            NightMode.NO -> setNightModeBtn(isNight = false)
            NightMode.YES -> setNightModeBtn(isNight = true)
            NightMode.AUTO -> {
                switchAutoMode.isChecked = true
                toggleSwitch.clearChecked()
                showSuccess(UI_MODE_NIGHT_UNDEFINED)
            }
            NightMode.UNKNOWN -> {
                showError()
            }
        }
    }

    private fun setNightModeBtn(isNight: Boolean){
        toggleSwitch.check(if(isNight) R.id.btnNight else R.id.btnDay)
        showSuccess(if(isNight) UI_MODE_NIGHT_YES else UI_MODE_NIGHT_NO)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        firebaseAnalytics.logEvent("onConfigurationChanged")
        if(APP_RATE_DIALOG_INTERVAL == 0)
            APP_RATE_DIALOG_INTERVAL = 5
        when (newConfig.uiMode and UI_MODE_NIGHT_MASK) {
            UI_MODE_NIGHT_YES -> isDarkThemeEnabled = true
            UI_MODE_NIGHT_NO -> isDarkThemeEnabled = false
        }
        recreate()
    }

    private fun showSuccess(uiModeNight: Int) {
        msgLy.visibility = View.VISIBLE
        when(uiModeNight){
            UI_MODE_NIGHT_YES -> msgTitle.text = this.getString(R.string.sw_info3)
            UI_MODE_NIGHT_NO -> msgTitle.text = this.getString(R.string.sw_info2)
            else ->  msgTitle.text = this.getString(R.string.sw_info1)
        }
        ivResult.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_check_circle_black_24dp))
        ivResult.setColorFilter(ContextCompat.getColor(this,R.color.green_400))
        msgLy.strokeColor = ContextCompat.getColor(this,R.color.green_200)
    }

    private fun showError() {
        msgLy.visibility = View.VISIBLE
        msgLy.strokeColor = ContextCompat.getColor(this,R.color.red_400)
        msgTitle.text = this.getString(R.string.sw_info4)
    }

    private fun getCurrentNightMode(): NightMode {
        return when (uiModeManager?.nightMode) {
            MODE_NIGHT_NO -> NightMode.NO
            MODE_NIGHT_YES -> NightMode.YES
            MODE_NIGHT_AUTO, MODE_NIGHT_AUTO_TIME, MODE_NIGHT_AUTO_BATTERY -> NightMode.AUTO
            else -> NightMode.UNKNOWN
        }
    }

    private fun setupBannerAd() {
        val adRequest = AdRequest.Builder().build()
        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
        adView.loadAd(adRequest)
    }
    private fun setupInterstitialAd() {
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd?.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd?.loadAd(AdRequest.Builder().build())
    }
    private fun showAd(){
        AppPreferences.themeChangeCount += 1
        if(AppPreferences.themeChangeCount % THEME_SWITCH_AD_INTERVAL == 0){
            Utils.showGoogleInterstitialAds(mInterstitialAd)
            mInterstitialAd?.loadAd(AdRequest.Builder().build())
        }
    }
}

sealed class NightMode {
    object YES: NightMode()
    object NO: NightMode()
    object AUTO: NightMode()
    object UNKNOWN: NightMode()
}
