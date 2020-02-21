package com.softinit.darkmode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_information.*

class InformationActivity : AppCompatActivity() {

    val SUCCESS = 1
    val ERROR = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)
        val message= intent?.getIntExtra("type",ERROR)
        if(message == SUCCESS){
            msgSuccess.visibility = View.VISIBLE
            msgError.visibility = View.GONE
        }
        else {
            msgSuccess.visibility = View.GONE
            msgError.visibility = View.VISIBLE
        }
    }
}
