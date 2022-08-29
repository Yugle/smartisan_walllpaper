package com.yugle.smartisanwallpaper.service

import android.app.*
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.yugle.smartisanwallpaper.BuildConfig
import com.yugle.smartisanwallpaper.MainActivity.Companion.SOURCE_LIST
import com.yugle.smartisanwallpaper.R
import com.yugle.smartisanwallpaper.Utils.getBitmapFromUrl
import com.yugle.smartisanwallpaper.Utils.getSettings
import java.io.IOException
import java.util.*

class ChangeWallpaperService : Service() {

    private var runningModeIsXposed = false
    private var changeFrequency = 900
    private var changeImmediately = false

    private val timer by lazy {
        Timer()
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service started.")

        getSettings()
        setBroadcastReceiver()

        val timerTask = object : TimerTask() {
            override fun run() {
                setWallpaper()
            }
        }
        var delay = 5000L
        if (BuildConfig.DEBUG) {
            timer.schedule(timerTask, delay, 50000L)
        } else {
            if (changeImmediately) {
                delay = changeFrequency * 1000L
            }
            timer.schedule(timerTask, delay, changeFrequency * 1000L)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel = NotificationChannel(
            getString(R.string.app_package_name),
            getString(R.string.app_name),
            IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(notificationChannel)
        val notification = Notification.Builder(this, getString(R.string.app_package_name))
            .setContentTitle(getText(R.string.app_name))
            .setContentText(getText(R.string.app_package_name))
            .build()
        startForeground(1, notification)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun getSettings() {
        val settings = getSettings(this)
        runningModeIsXposed = settings.RUNNING_MODE_SETTING_KEY
        changeFrequency = settings.CHANGE_FREQUENCY_SETTING_KEY
        changeImmediately = settings.CHANGE_IMMEDIATELY_SETTING_KEY
    }

    private fun setBroadcastReceiver() {
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d(TAG, "Got a broadcast to set a wallpaper.")
                setWallpaper()
            }
        }
        val filter = IntentFilter().apply {
            addAction(SET_WALLPAPER_IMMEDIATELY_BROADCAST)
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter)
    }

    private fun setWallpaper() {
        val wallpaperManager = WallpaperManager.getInstance(this)

        Thread {
            try {
                val url = SOURCE_LIST[(Math.random() * SOURCE_LIST.size).toInt()]
                Log.d(TAG, "Try to load bitmap from url $url.")
                val bitmap = getBitmapFromUrl(url)
                if (bitmap != null) {
                    Log.d(TAG, "Try to set the wallpaper.")
                    wallpaperManager.setBitmap(bitmap)
                }
            } catch (e: IOException) {
                Log.e(TAG, "Failed to set a wallpaper.", e)
                e.printStackTrace()
            }
        }.start()
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "ChangeWallpaperService"

        const val SET_WALLPAPER_IMMEDIATELY_BROADCAST =
            "com.yugle.smartisanwallpaper.SET_WALLPAPER_IMMEDIATELY"

        fun isServiceRunning(context: Context, className: String): Boolean {
            var isRunning = false
            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val serviceList =
                activityManager.getRunningServices(30) as List<ActivityManager.RunningServiceInfo>

            if (serviceList.isEmpty()) {
                return false
            }

            for (service in serviceList) {
                if (service.service.className == className) {
                    isRunning = true
                    break
                }
            }

            return isRunning
        }
    }
}