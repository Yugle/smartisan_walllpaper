package com.yugle.smartisanwallpaper.xposed

import android.content.Context
import android.content.Intent
import com.yugle.smartisanwallpaper.service.ChangeWallpaperService
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.XposedHelpers.findClass
import de.robv.android.xposed.callbacks.XC_LoadPackage

class Core : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
//        val timer = Timer()
//        val timerTask = object : TimerTask() {
//            override fun run() {
//                AndroidAppHelper.currentApplication().applicationContext
//            }
//        }
//        timer.schedule(timerTask, 10000L, 10000L)
//        if (lpparam?.packageName == "tv.danmaku.bili") {
//            timer.cancel()
//        }
        var applicationContext: Context? = null
        try {
            val contextClass = findClass("android.content.ContextWrapper", lpparam?.classLoader);
            findAndHookMethod(contextClass, "getApplicationContext", object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    super.afterHookedMethod(param)
                    if (applicationContext != null)
                        return
                    applicationContext = param.result as Context
                    XposedBridge.log("Context is gotten.$applicationContext")
                    val intent = Intent(applicationContext, ChangeWallpaperService::class.java)
                    applicationContext?.startForegroundService(intent)
                }
            });
        } catch (e: Exception) {
            XposedBridge.log("Failed to get context.")
            XposedBridge.log(e)
        }
//        if (applicationContext != null && !isServiceRunning(
//                applicationContext!!,
//                "ChangeWallpaperService"
//            )
//        ) {
    }
}