package com.softinit.darkmode

import android.app.UiModeManager
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Configuration.*
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_view.*
import kotlinx.android.synthetic.main.main_view.*

// onConfig changed wouldn't be called if you are try to set day mode on day mode and vice versa
class MainActivity : AppCompatActivity() {

    private var uiModeManager: UiModeManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        uiModeManager = getSystemService(UI_MODE_SERVICE) as UiModeManager
        initiateLayout()
    }

    fun initiateLayout(){
        nav_view.setNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.nav_more_apps -> Utils.openDevPage(this)
                R.id.nav_rate_app -> AppRatingDialog.getDialog(this, false).show()
                R.id.nav_share -> startActivity(Utils.share(this))
                R.id.nav_feedback -> Utils.sendFeedback(this)
                R.id.nav_faq -> startActivity(Intent(this,FaqActivity::class.java))
                R.id.nav_privacypolicy -> Utils.openBrowser(this,"")
                R.id.nav_about_us -> startActivity(Intent(this, AboutActivity::class.java))
            }
            true
        }
        ivFaq.setOnClickListener {
            startActivity(Intent(this,FaqActivity::class.java))
        }
        btnDay.setOnClickListener {
            if(switchAutoMode.isChecked) switchAutoMode.isChecked = false
            setNightModeBtn(isNight = false)
            uiModeManager?.nightMode = UiModeManager.MODE_NIGHT_NO
            showError()
        }
        btnNight.setOnClickListener {
            if(switchAutoMode.isChecked) switchAutoMode.isChecked = false
            setNightModeBtn(isNight = true)
            uiModeManager?.nightMode = UiModeManager.MODE_NIGHT_YES
            showError()
        }
        switchAutoMode.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                btnNight.isSelected = false
                btnDay.isSelected = false
                uiModeManager?.nightMode = UiModeManager.MODE_NIGHT_AUTO
            }
            else{
                when(isUsingNightModeResources()){
                    NightMode.YES -> {
                        btnNight.isSelected = true
                        uiModeManager?.nightMode = UiModeManager.MODE_NIGHT_YES
                    }
                    NightMode.NO -> {
                        btnDay.isSelected = true
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
                getString(R.string.sw_info2),getString(R.string.sw_info3) -> intent.putExtra("type",UI_MODE_NIGHT_YES)
                getString(R.string.sw_info4) -> intent.putExtra("type",UI_MODE_NIGHT_UNDEFINED)
            }
            startActivity(intent)
        }
        ivIconMenu.setOnClickListener {
            drawer_layout.openDrawer(Gravity.LEFT)
        }
        rvSupportedAppsList.layoutManager = GridLayoutManager(this,4)
        rvSupportedAppsList.adapter = SupportedAppsAdapter(this, getAppsList(this))
        setInitialMode()
    }

    private fun setInitialMode(){
        when (getCurrentNightMode()) {
            NightMode.NO -> setNightModeBtn(isNight = false)
            NightMode.YES -> setNightModeBtn(isNight = true)
            NightMode.AUTO -> {
                switchAutoMode.isChecked = true
                btnDay.isSelected = false
                btnNight.isSelected = false
                showSuccess(UI_MODE_NIGHT_UNDEFINED)
            }
            NightMode.UNKNOWN -> {
                showError()
            }
        }
    }

    private fun setNightModeBtn(isNight: Boolean){
        btnNight.isSelected = isNight
        btnDay.isSelected = !isNight
        showSuccess(if(isNight) UI_MODE_NIGHT_YES else UI_MODE_NIGHT_NO)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        /*when (newConfig.uiMode and UI_MODE_NIGHT_MASK) {
            UI_MODE_NIGHT_YES -> showSuccess(UI_MODE_NIGHT_YES)
            UI_MODE_NIGHT_NO -> showSuccess(UI_MODE_NIGHT_NO)
            UI_MODE_NIGHT_UNDEFINED -> showError()
        }*/
        recreate()
    }

    private fun showSuccess(uiModeNight: Int) {
        msgLy.visibility = View.VISIBLE
        when(uiModeNight){
            UI_MODE_NIGHT_YES -> msgTitle.text = this.getString(R.string.sw_info3)
            UI_MODE_NIGHT_NO -> msgTitle.text = this.getString(R.string.sw_info2)
            else ->  msgTitle.text = this.getString(R.string.sw_info1)
        }
        msgLy.strokeColor = resources.getColor(R.color.switchBgColor)
    }

    private fun showError() {
        msgLy.visibility = View.VISIBLE
        msgLy.strokeColor = resources.getColor(R.color.red)
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

    private fun isUsingNightModeResources(): NightMode {
        return when (resources.configuration.uiMode and UI_MODE_NIGHT_MASK) {
            UI_MODE_NIGHT_YES -> NightMode.YES
            UI_MODE_NIGHT_NO -> NightMode.NO
            UI_MODE_NIGHT_UNDEFINED -> NightMode.UNKNOWN
            else -> NightMode.UNKNOWN
        }
    }
}

sealed class NightMode {
    object YES: NightMode()
    object NO: NightMode()
    object AUTO: NightMode()
    object UNKNOWN: NightMode()
}
