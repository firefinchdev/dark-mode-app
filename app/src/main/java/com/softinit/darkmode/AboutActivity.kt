package com.softinit.darkmode


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.softinit.darkmode.AppConstants.PRIVACY_POLICY_URL
import com.softinit.darkmode.AppConstants.TERMS_CONDITIONS_URL
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvAppVersion.text = Utils.getAppVersion(this)
        clConnect.setOnClickListener {
            Utils.openBrowser(this,"https://www.linkedin.com/company/sofknit")
        }
        clFeedback.setOnClickListener {
            Utils.sendFeedback(this)
        }
        clOtherApps.setOnClickListener {
            Utils.openDevPage(this)
        }
        clRateus.setOnClickListener {
            AppRatingDialog.getDialog(this, false).show()
        }
        btmTnC.setOnClickListener {
            Utils.openBrowser(this,TERMS_CONDITIONS_URL)
        }
        btnPolicy.setOnClickListener {
            Utils.openBrowser(this,PRIVACY_POLICY_URL)
        }
        btnOpenSource.setOnClickListener {
            startActivity(Intent(this, OssLicensesMenuActivity::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                onBackPressed()
        }
        return true
    }
}
