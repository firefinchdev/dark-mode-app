package com.softinit.darkmode

import com.preference.PowerPreference
import com.preference.Preference

object AppPreferences: AppPref() {
    private val res = App.getAppResources()
    override val preference: Preference = PowerPreference.getDefaultFile()

    var firstStart
            by Boolean(res.getString(R.string.preference_first_start), true)

    var isDarkThemeEnabled
            by Boolean(res.getString(R.string.key_preference_dark_mode), false)

    var userSessionCount by Int(res.getString(R.string.key_user_session_count),1)
}