package com.softinit.darkmode

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.softinit.darkmode.Utils.openAppOnPlayStore

class AppSelectorItem (
    val appId:String,
    val appName:String,
    val appIcon: Drawable?
)

fun getAppsList(context: Context):List<AppSelectorItem>{
    val appsList = mutableListOf<AppSelectorItem>()
    appsList.add(AppSelectorItem("com.instagram.android",context.getString(R.string.insgram),context.getDrawable(R.drawable.insgram)))
    appsList.add(AppSelectorItem("com.android.vending",context.getString(R.string.google_play),context.getDrawable(R.drawable.google_play)))
    appsList.add(AppSelectorItem("com.android.chrome",context.getString(R.string.chrome),context.getDrawable(R.drawable.chrome)))
    appsList.add(AppSelectorItem("com.google.android.apps.photos",context.getString(R.string.photos),context.getDrawable(R.drawable.photos)))
    appsList.add(AppSelectorItem("com.google.android.apps.walletnfcrel",context.getString(R.string.google_pay),context.getDrawable(R.drawable.google_pay)))
    appsList.add(AppSelectorItem("com.google.android.apps.fitness",context.getString(R.string.fitness),context.getDrawable(R.drawable.fitness)))
    appsList.add(AppSelectorItem("au.com.shiftyjelly.pocketcasts",context.getString(R.string.pocket_casts),context.getDrawable(R.drawable.pocketcasts)))
    appsList.add(AppSelectorItem("com.microsoft.launcher",context.getString(R.string.ms_launcher),context.getDrawable(R.drawable.microsoft_auncher)))
    return appsList
}

class SupportedAppsAdapter(val context: Context?,val appsList: List<AppSelectorItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AppDetailsViewHolder(LayoutInflater.from(context).inflate(R.layout.supported_apps_item, parent, false))
    }

    override fun getItemCount(): Int {
        return appsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, i: Int) {
        val appDetailsViewHolder: AppDetailsViewHolder = holder as AppDetailsViewHolder
        appDetailsViewHolder.appIcon.setImageDrawable(appsList[i].appIcon)
        appDetailsViewHolder.appName.text = appsList[i].appName
        appDetailsViewHolder.supportedAppItem.setOnClickListener {
            context?.let { context -> openAppOnPlayStore(context,appsList[i].appId) }
        }
    }

    inner class AppDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var appName: TextView = itemView.findViewById(R.id.tvAppName)
        internal var appIcon: ImageView = itemView.findViewById(R.id.ivAppIcon)
        internal var supportedAppItem: RelativeLayout = itemView.findViewById(R.id.supportedAppItem)
    }

}