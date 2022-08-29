package com.yugle.smartisanwallpaper.data

data class Settings(
    val RUNNING_MODE_SETTING_KEY: Boolean = false,
    val CHANGE_FREQUENCY_SETTING_KEY: Int = 900,
    val CHANGE_IMMEDIATELY_SETTING_KEY: Boolean = false,
    val SHOW_LAUNCH_TIP: Boolean = true
) {
    companion object {
        const val RUNNING_MODE_SETTING_KEY =
            "running_mode" // true is xposed, false is front service
        const val CHANGE_FREQUENCY_SETTING_KEY = "change_frequency"
        const val CHANGE_IMMEDIATELY_SETTING_KEY = "change_immediately"
        const val SHOW_LAUNCH_TIP_KEY = "show_launch_tip"
    }
}