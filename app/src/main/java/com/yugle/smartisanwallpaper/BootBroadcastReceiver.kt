package com.yugle.smartisanwallpaper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yugle.smartisanwallpaper.service.ChangeWallpaperService

class BootBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(ACTION_BOOT_COMPLETED)) {
//            context?.startActivity(
//                Intent(
//                    context,
//                    MainActivity::class.java
//                ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            )
            // TODO("Use WorkManager.")
            context?.startForegroundService(Intent(context, ChangeWallpaperService::class.java))
        }
    }

    companion object {
        private const val ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED"
    }
}