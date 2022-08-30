package com.yugle.smartisanwallpaper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.yugle.smartisanwallpaper.data.Settings
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

object Utils {

    fun getBitmapFromUrl(url: String): Bitmap? {
        return try {
            val connection = URL(url).openConnection()
            connection.doInput = true
            connection.useCaches = false
            connection.connect()
            val inputStream = connection.getInputStream()
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun downloadFile(context: Context, url: String): String? {
        var filePath: String? = null
        try {
            val fileName = url.split("/").last()
            Log.d("1123", fileName)
            filePath = context.getExternalFilesDir(null)?.path + fileName
            val connection = URL(url).openConnection()
            connection.doInput = true
            connection.useCaches = false
            connection.connect()
            val inputStream = connection.getInputStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val fileOutputStream =
                FileOutputStream(filePath)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return filePath
    }

    fun getSettings(context: Context): Settings {
        val sharedPreferences = context.getSharedPreferences(
            context.getString(R.string.app_package_name),
            Context.MODE_PRIVATE
        )
        return Settings(
            sharedPreferences.getBoolean(Settings.RUNNING_MODE_SETTING_KEY, true),
            sharedPreferences.getInt(Settings.CHANGE_FREQUENCY_SETTING_KEY, 900),
            sharedPreferences.getBoolean(Settings.CHANGE_IMMEDIATELY_SETTING_KEY, false),
            sharedPreferences.getBoolean(Settings.SHOW_LAUNCH_TIP_KEY, true),
        )
    }

    fun writeSetting(context: Context, key: String, value: Any) {
        with(
            context.getSharedPreferences(
                context.getString(R.string.app_package_name),
                Context.MODE_PRIVATE
            ).edit()
        ) {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                else -> {}
            }
            apply()
        }
    }
}