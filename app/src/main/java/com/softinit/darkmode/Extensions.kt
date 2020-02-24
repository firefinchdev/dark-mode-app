package com.softinit.darkmode

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.analytics.FirebaseAnalytics

fun FirebaseAnalytics.logEvent(
    eventName: String,
    itemId: String? = null,
    itemName: String? = null,
    contentType: String? = null) {
    val bundle = Bundle().apply {
        if(itemId != null) putString(FirebaseAnalytics.Param.ITEM_ID, itemId)
        if(itemId != null) putString(FirebaseAnalytics.Param.ITEM_NAME, itemName)
        if(itemId != null) putString(FirebaseAnalytics.Param.CONTENT_TYPE, contentType)
    }
    logEvent(eventName, bundle)
}
