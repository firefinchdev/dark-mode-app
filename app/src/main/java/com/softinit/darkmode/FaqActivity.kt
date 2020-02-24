package com.softinit.darkmode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_faq.*

class FaqActivity : AppCompatActivity() {

    lateinit var faqDetailsAdapter: FaqDetailsAdapter
    var hashMap: HashMap<String, List<String>> = hashMapOf()
    private var appInterstitialAd: AppInterstitialAd? = null
    val firebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        hashMap = FaqData.getChildHelpData(this)
        faqDetailsAdapter = FaqDetailsAdapter(
            this,
            FaqData.getGroupHelpData(this),
            hashMap
        )
        lvFaq.setAdapter(faqDetailsAdapter)
        ic_back.setOnClickListener {
            onBackPressed()
        }
        appInterstitialAd = newAppInterstitialAdConditional(this,
            "792185624602570_792196721268127",
            firebaseAnalytics,
            "AdFaqActivity",
            showInterval = 1,
            destroyOnDismiss = true
        )
        Utils.setStatusBarIconsColor(this)
    }
    override fun onBackPressed() {
        appInterstitialAd?.showIfValidLoadedInterval()
        finish()
    }
    override fun onDestroy() {
        appInterstitialAd?.destroy()
        super.onDestroy()
    }
}
