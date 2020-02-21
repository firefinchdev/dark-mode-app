package com.softinit.darkmode

import android.app.UiModeManager
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Configuration.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_view.*
import kotlinx.android.synthetic.main.main_view.*

class MainActivity : AppCompatActivity() {

    private lateinit var uiModeManager: UiModeManager
    val NIGHT_MODE = 1
    val NO_NIGHT_MODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initiateLayout()
        uiModeManager = getSystemService(UI_MODE_SERVICE) as UiModeManager
        when (isUsingNightModeResources()) {
            NightMode.NO -> {
                btnDay.isSelected = true
                btnNight.isSelected = false
            }
            NightMode.YES -> {
                btnNight.isSelected = true
                btnDay.isSelected = false
            }
            NightMode.UNKNOWN -> {
                showError()
            }
        }

        /*btnDarkMode.setOnClickListener {
            uiModeManager.nightMode = when (getCurrentNightMode()){
                NightMode.NO -> UiModeManager.MODE_NIGHT_YES
                NightMode.YES -> UiModeManager.MODE_NIGHT_NO
                NightMode.AUTO -> UiModeManager.MODE_NIGHT_YES
                NightMode.UNKNOWN -> {
                    showError()
                    return@setOnClickListener
                }
            }
            showError()
        }*/
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
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.uiMode and UI_MODE_NIGHT_MASK) {
            UI_MODE_NIGHT_YES -> showSuccess()
            UI_MODE_NIGHT_NO -> showSuccess()
            UI_MODE_NIGHT_UNDEFINED -> showError()
        }
        Toast.makeText(this, "Loda Lehsun Config Change Detected", Toast.LENGTH_LONG).show()
    }

    private fun showSuccess() {

    }

    private fun showError() {
        msgLy.visibility = View.VISIBLE
        msgTitle.text = this.getString(R.string.sw_info4)
    }

    private fun getCurrentNightMode(): NightMode {
        return when (uiModeManager.nightMode) {
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
