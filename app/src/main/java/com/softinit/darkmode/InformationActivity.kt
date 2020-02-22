package com.softinit.darkmode

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_UNDEFINED
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_information.*
import java.lang.Exception


class InformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        when(intent?.getIntExtra("type", UI_MODE_NIGHT_UNDEFINED)){
            UI_MODE_NIGHT_YES -> {
                msgSuccess.visibility = View.VISIBLE
                msgError.visibility = View.GONE
            }
            UI_MODE_NIGHT_UNDEFINED -> {
                msgSuccess.visibility = View.GONE
                msgError.visibility = View.VISIBLE
                when {
                    Build.VERSION.SDK_INT < 26 -> {
                        msg_subLy.visibility = View.GONE
                    }
                    else -> msg_subLy.visibility = View.VISIBLE
                }
            }
        }
        btn1.setOnClickListener {
            startActivity(Intent(this,FaqActivity::class.java))
        }
        btn2.setOnClickListener {
            startActivity(Intent(this,FaqActivity::class.java))
        }
        btn3.setOnClickListener {
            try {
                if (Build.VERSION.SDK_INT >= 26) {
                    startActivityForResult(Intent("android.settings.SETTINGS"), 0)
                }
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
        Utils.setStatusBarIconsColor(this)
    }
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}
