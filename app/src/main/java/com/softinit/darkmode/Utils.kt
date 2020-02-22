package com.softinit.darkmode

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.softinit.darkmode.AppConstants.DEVELOPER_EMAIL

object Utils {
    fun openDevPage(context: Context) {
        val uri = Uri.parse("market://search?q=pub:"+"softinit")
        val i = Intent(Intent.ACTION_VIEW, uri)
        i.flags = Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        try {
            context.startActivity(i)
        } catch (e: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/search?q=pub:"+"softinit")
                )
            )
        }
    }
    fun share(context: Context): Intent = Intent(Intent.ACTION_SEND)
        .apply {
            putExtra(
                Intent.EXTRA_TEXT, context.getString(R.string.app_share)
            )
            type = "text/plain"
        }
    fun sendFeedback(context: Context,feedback: String = "") {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(DEVELOPER_EMAIL))
        intent.putExtra(
            Intent.EXTRA_SUBJECT,
            "Feedback for ${getAppName(context)}"
        )
        intent.putExtra(Intent.EXTRA_TEXT, feedback)
        val activityInfo = intent.resolveActivityInfo(context.packageManager, intent.flags)
        activityInfo?.let { activityInfo ->
            if (activityInfo.exported) {
                context.startActivity(intent)
            }
        }
    }
    fun getAppName(context: Context): String {
        return context.getString(R.string.app_name)
    }
    fun openBrowser(context: Context,url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW)
        browserIntent.data = Uri.parse(url)
        val activityInfo = browserIntent.resolveActivityInfo(context.packageManager, browserIntent.flags)
        activityInfo?.let { activityInfo ->
            if (activityInfo.exported) {
                context.startActivity(browserIntent)
            }
        }
    }
    fun getAppVersion(context: Context):String{
        val manager = context.packageManager
        val info = manager.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
        return info.versionName
    }
    fun openAppOnPlayStore(context: Context, pkgName: String){
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$pkgName")))
        } catch (anfe: ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$pkgName")))
        }
    }
    fun setStatusBarIconsColor(context: Context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = (context as Activity).window.decorView
            when(isUsingNightModeResources(context)){
                NightMode.NO -> decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                NightMode.YES -> decor.systemUiVisibility = 0
            }
        }
    }
    fun isUsingNightModeResources(context: Context): NightMode {
        return when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> NightMode.YES
            Configuration.UI_MODE_NIGHT_NO -> NightMode.NO
            Configuration.UI_MODE_NIGHT_UNDEFINED -> NightMode.UNKNOWN
            else -> NightMode.UNKNOWN
        }
    }
}