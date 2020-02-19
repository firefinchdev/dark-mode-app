package com.softinit.darkmode

import android.content.Context

object FaqData {

    fun getGroupHelpData(context: Context):List<String>{
        val appName = Utils.getAppName(context)
        val listDataHeader = mutableListOf<String>()
        //Header data
        listDataHeader.add(context.getString(R.string.updating_miui_error_ques))
        listDataHeader.add(context.getString(R.string.samsung_galaxy_error_ques))
        listDataHeader.add(context.getString(R.string.app_not_working_ques))
        listDataHeader.add(context.getString(R.string.app_not_supported_ques))
        listDataHeader.add(context.getString(R.string.which_apps_supported_ques))
        listDataHeader.add(context.getString(R.string.instagram_error_ques))
        listDataHeader.add(context.getString(R.string.want_light_mode_back_ques))
        listDataHeader.add(context.getString(R.string.dark_mode_turnedoff_ques))
        listDataHeader.add(context.getString(R.string.auto_mode_ques))
        listDataHeader.add(context.getString(R.string.remove_ads_ques))
        return listDataHeader
    }

    fun getChildHelpData(context: Context):HashMap<String,List<String>>{
        val appName = Utils.getAppName(context)
        val listDataHeader =
            getGroupHelpData(
                context
            )
        val hashMap:HashMap<String,List<String>> = hashMapOf()

        //Child data
        hashMap[listDataHeader[0]] = listOf("<p>The Android dark mode functionality was disabled in MIUI 11 and can't be re-activated by third-party apps like Dark Mode.</p>\n\t\t\t\t\t<p>There is a thread in the MIUI Community Forum that reports this behavior as a bug in MIUI 11. If you are affected by this issue, feel free to share your experience there: <a href=\"https://c.mi.com/thread-2626609-1-0.html\">https://c.mi.com/thread-2626609-1-0.html</a></p>\n\t\t\t\t\t<p>The more visibility this issue receives, the more likely it will be fixed soon.</p>\n\t\t\t\t\t<p><span style=\"color: #ff5e6e;\">Update:</span> Several solutions for this issue were found by the community.</p>\n\t\t\t\t\t<p><b>First Option:</b></p>\n\t\t\t\t\t<ol>\n\t\t\t\t\t\t<li>Open the security app</li>\n\t\t\t\t\t\t<li>Start a scan</li>\n\t\t\t\t\t\t<li>Continue with an optimization</li>\n\t\t\t\t\t\t<li>In the end, you will be notified that some elements are not displayed correctly</li>\n\t\t\t\t\t\t<li>Press \"Fix it\" to restore the day mode</li>\n\t\t\t\t\t</ol>\n\t\t\t\t\t<p><b>Second Option:</b></p>\n\t\t\t\t\t<p>Unfortunately, the last option is to do a factory reset of the phone. Warning: This deletes all data from your phone!</p>")
        hashMap[listDataHeader[1]] = listOf("<p>Since Nov. 21, 2019 some Samsung Galaxy devices repeatedly switch back to day mode. This not an issue with the Dark Mode app but with the Email app by Samsung.</p>\n\t\t\t\t\t<p>The Samsung Email app overwrites the Android night mode settings which may switch your device back to day mode. This happens whenever the app is opened and sometimes even when the app is running in the background.</p>\n\t\t\t\t\t<p><b>Solution 1</b> - Disable the Samsung Email app</p>\n\t\t\t\t\t<ol>\n\t\t\t\t\t\t<li>Open your Android Settings</li>\n\t\t\t\t\t\t<li>Click \"Apps\"</li>\n\t\t\t\t\t\t<li>Select the Email app</li>\n\t\t\t\t\t\t<li>Click on the settings ⋮ in the top right corner</li>\n\t\t\t\t\t\t<li>Click on \"Uninstall updates\"</li>\n\t\t\t\t\t\t<li>Click \"OK\" in the dialog</li>\n\t\t\t\t\t</ol>\n\t\t\t\t\t<p><b>Solution 2</b> - Disable the dark mode settings of the Samsung Email app</p>\n\t\t\t\t\t<ol>\n\t\t\t\t\t\t<li>Open the Samsung Email app</li>\n\t\t\t\t\t\t<li>Open the menu ☰ in the top left corner</li>\n\t\t\t\t\t\t<li>Click on the settings <img style=\"height: .8em;\" src=\"gear.svg\" alt=\"gear\"> in the top right corner</li>\n\t\t\t\t\t\t<li>Enable \"Night mode\"</li>\n\t\t\t\t\t\t<li style=\"list-style-type: none; margin-top: .5em;\">Now the Email app will still overwrite the Android dark mode settings but, instead of activating the day mode, it will activate the night mode.</li>\n\t\t\t\t\t</ol>\n\t\t\t\t\t<p>Note: Keep in mind that there may be other apps on your phone which have similar issues.</p>")
        hashMap[listDataHeader[2]] = listOf("<p>Most likely, your phone manufacturer has disabled the Android dark mode on your phone. A number of phones are affected by this issue, for example, a few specific Samsung Galaxy and Xiaomi Redmi phones.</p>\n\t\t\t\t\t<p>Unfortunately, there is nothing I can do to fix this. I'm sorry about that. \uD83D\uDE14</p>\n\t\t\t\t\t<p>If you are lucky, your phone manufacturer will enable the dark mode with a future update.</p>")
        hashMap[listDataHeader[3]] = listOf("<p>The Dark Mode app supports all apps that have a dark theme. If an app is not turning dark, this means that the app does not have a dark theme.</p>\n\t\t\t\t\t<p>Unfortunately, there is no way for me to implement a dark theme for that app - only its owner can do so. If you really need the dark mode for that app, I'd recommend contacting its owner. Chances are they are already working on a dark theme for their app. \uD83D\uDE0A</p>")
        hashMap[listDataHeader[4]] = listOf("<p>The Dark Mode app supports all apps that have a dark theme.</p>\n\t\t\t\t\t<p>Popular examples include <i>Google Chrome</i>, <i>Google Play</i> and <i>Instagram</i>. <i>WhatsApp</i> is currently working on a dark theme that will hopefully be released soon.</p>")
        hashMap[listDataHeader[5]] = listOf("<p>Instagram got a dark theme in October 2019.</p>\n\t\t\t\t\t<p>If your Instagram is still light, chances are your phone is too old to receive the latest Instagram updates.</p>")
        hashMap[listDataHeader[7]] = listOf("\n\t\t\t\t\t<p>There are multiple possibilities:</p>\n\t\t\t\t\t<ul>\n\t\t\t\t\t\t<li>You have selected \"Auto\" rather than \"Night Mode\". \"Auto\" may look dark at first, but it actually toggles the mode automatically depending on the current time: dark at night, light by day.</li>\n\t\t\t\t\t\t<li>The mode was turned off by another app that is overwriting the Android dark mode settings.</li>\n\t\t\t\t\t\t<li>The mode was turned off by whoever used your phone while you weren't looking. \uD83D\uDE48</li>\n\t\t\t\t\t</ul>")
        hashMap[listDataHeader[8]] = listOf("\n\t\t\t\t\t<p>The Auto mode enables the night mode in the evening and the day mode in the morning.</p>\n\t\t\t\t\t<p>Unfortunately, some phone manufacturers changed or removed this behavior in their customized android versions.</p>\n\t\t\t\t\t<p> In case the auto mode does not work on your device, your phone manufacturer has probably disabled this mode.</p>")
        // To be added at runtime
        hashMap[listDataHeader[9]] = listOf("\n\t\t\t\t\t<p>I don't like ads either, but unfortunately, I cannot afford working on this app for several hours a day – without earning any money.</p>\n\t\t\t\t\t<p>Any support is much appreciated! ❤️</p>")
        hashMap[listDataHeader[6]] = listOf("\t\t\t\t\t<ol>\n\t\t\t\t\t\t<li>Install the app again.</li>\n\t\t\t\t\t\t<li>Select light mode.</li>\n\t\t\t\t\t\t<li>Uninstall the app again.</li>\n\t\t\t\t\t</ol>")
        return hashMap
    }
}