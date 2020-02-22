package com.softinit.darkmode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_faq.*

class FaqActivity : AppCompatActivity() {

    lateinit var faqDetailsAdapter: FaqDetailsAdapter
    var hashMap: HashMap<String, List<String>> = hashMapOf()
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
            finish()
        }
        Utils.setStatusBarIconsColor(this)
    }
}
