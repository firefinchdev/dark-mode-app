package com.softinit.darkmode

import android.app.UiModeManager
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Configuration.*
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import com.facebook.ads.AdSize
import com.google.firebase.analytics.FirebaseAnalytics
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch
import com.softinit.darkmode.AppConstants.APP_RATE_DIALOG_INTERVAL
import com.softinit.darkmode.AppPreferences.firstStart
import com.softinit.darkmode.AppPreferences.isDarkThemeEnabled
import com.softinit.darkmode.AppPreferences.userSessionCount
import com.softinit.darkmode.Utils.isUsingNightModeResources
import com.softinit.darkmode.Utils.setStatusBarIconsColor
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_view.*


// onConfig changed wouldn't be called if you are try to set day mode on day mode and vice versa
class MainActivity : AppCompatActivity() {

    private var uiModeManager: UiModeManager?=null
    private var appInterstitialAd: AppInterstitialAd? = null
    private var adView: AppAdView? = null
    val firebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        uiModeManager = getSystemService(UI_MODE_SERVICE) as UiModeManager
        initiateLayout()
        setupBannerAd()
        setNewInstanceInterstitialAd()
    }

    override fun onResume() {
        super.onResume()
        appInterstitialAd?.showIfValidLoadedInterval()
    }

    override fun onDestroy() {
        appInterstitialAd?.destroy()
        super.onDestroy()
    }

    fun initiateLayout(){
        ivFaq.setOnClickListener {
            startActivity(Intent(this,FaqActivity::class.java))
        }
        toggleSwitch.onChangeListener = object : ToggleSwitch.OnChangeListener {
            override fun onToggleSwitchChanged(position: Int) {
                when(position){
                    0 -> {
                        if(switchAutoMode.isChecked) switchAutoMode.isChecked = false
                        setNightModeBtn(isNight = false)
                        uiModeManager?.nightMode = UiModeManager.MODE_NIGHT_NO
                        showError()
                    }
                    1 -> {
                        if(switchAutoMode.isChecked) switchAutoMode.isChecked = false
                        setNightModeBtn(isNight = true)
                        uiModeManager?.nightMode = UiModeManager.MODE_NIGHT_YES
                        showError()
                    }
                }
            }
        }
        switchAutoMode.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                toggleSwitch.checkedPosition = -1
                uiModeManager?.nightMode = UiModeManager.MODE_NIGHT_AUTO
            }
            else{
                when(isUsingNightModeResources(this)){
                    NightMode.YES -> {
                        toggleSwitch.checkedPosition = 1
                        uiModeManager?.nightMode = UiModeManager.MODE_NIGHT_YES
                    }
                    NightMode.NO -> {
                        toggleSwitch.checkedPosition = 0
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
                toggleSwitch.checkedPosition = -1
                showSuccess(UI_MODE_NIGHT_UNDEFINED)
            }
            NightMode.UNKNOWN -> {
                showError()
            }
        }
    }

    private fun setNightModeBtn(isNight: Boolean){
        toggleSwitch.checkedPosition = if(isNight) 1 else 0
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
        //msgLy.strokeColor = resources.getColor(R.color.switchBgColor)
    }

    private fun showError() {
        msgLy.visibility = View.VISIBLE
        //msgLy.strokeColor = resources.getColor(R.color.red)
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
    private fun setNewInstanceInterstitialAd() {
        appInterstitialAd = newAppInterstitialAdConditional(
            this,
            "792185624602570_792186791269120",
            firebaseAnalytics,
            "AdMainActivity",
            showInterval = 5
        ).also {
            it?.onDismiss = {
                setNewInstanceInterstitialAd()
            }
        }
    }
    private fun setupBannerAd() {
        adView = newAppAdViewConditional(this,
            "792185624602570_794108871076912",
            AdSize.BANNER_HEIGHT_50,
            firebaseAnalytics,
            "BannerAdMainActiv")
        adView?.let { adView ->
            bannerContainer.addView(adView)
            adView.loadAd()
        }
    }
}

sealed class NightMode {
    object YES: NightMode()
    object NO: NightMode()
    object AUTO: NightMode()
    object UNKNOWN: NightMode()
}
