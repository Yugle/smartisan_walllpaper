package com.yugle.smartisanwallpaper.data

data class Settings(
    val RUNNING_MODE_SETTING_KEY: Boolean = false,
    val CHANGE_FREQUENCY_SETTING_KEY: Int = 900
) {
    companion object {
        const val RUNNING_MODE_SETTING_KEY =
            "running_mode" // true is xposed, false is front service
        const val CHANGE_FREQUENCY_SETTING_KEY = "change_frequency"
    }
}