package com.softinit.darkmode

import android.app.UiModeManager
import android.content.res.Configuration
import android.content.res.Configuration.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var uiModeManager: UiModeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        uiModeManager = getSystemService(UI_MODE_SERVICE) as UiModeManager

        btnDarkMode.setOnClickListener {
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
        tvErrorMsg.visibility = View.GONE
        tvSuccessMsg.visibility = View.VISIBLE
    }

    private fun showError() {
        tvErrorMsg.visibility = View.VISIBLE
        tvSuccessMsg.visibility = View.GONE
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