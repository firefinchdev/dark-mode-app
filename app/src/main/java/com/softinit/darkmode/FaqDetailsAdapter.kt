package com.softinit.darkmode

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView

class FaqDetailsAdapter(
    val context: Context?,
    val listDataHeader: List<String>,
    val listDataChild: HashMap<String, List<String>>
) : BaseExpandableListAdapter() {

    override fun getChild(groupPosition: Int, childPosititon: Int): String {
        return listDataChild[listDataHeader[groupPosition]]?.get(childPosititon) ?: ""
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int, childPosition: Int,
        isLastChild: Boolean, view: View?, parent: ViewGroup
    ): View {
        var convertView: View? = view
        val childText: String = getChild(groupPosition, childPosition)

        if (convertView == null) {
            convertView =
                LayoutInflater.from(context).inflate(R.layout.list_item_help, parent, false)
        }

        val tvItem = convertView?.findViewById<TextView>(R.id.tvItem)
        tvItem?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            it.text = Html.fromHtml(childText,Html.FROM_HTML_MODE_COMPACT)
            else it.text = Html.fromHtml(childText)
        }
        return convertView ?: View(context)
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return listDataChild[listDataHeader[groupPosition]]?.size ?: 0
    }

    override fun getGroup(groupPosition: Int): String {
        return listDataHeader[groupPosition]
    }

    override fun getGroupCount(): Int {
        return listDataHeader.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        view: View?,
        parent: ViewGroup
    ): View {
        val headerTitle = getGroup(groupPosition)
        var convertView: View? = view
        if (convertView == null) {
            convertView =
                LayoutInflater.from(context).inflate(R.layout.list_group_help, parent, false)
        }
        val imgExpandCollapse = convertView?.findViewById<ImageView>(R.id.ivGroup)

        // check if GroupView is expanded and set imageview for expand/collapse-action
        if (isExpanded) {
            imgExpandCollapse?.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp)
        } else {
            imgExpandCollapse?.setImageResource(R.drawable.ic_chevron_right_black_24dp)
        }
        val tvGroup = convertView?.findViewById<TextView>(R.id.tvGroup)
        tvGroup?.text = headerTitle
        return convertView ?: View(context)
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}