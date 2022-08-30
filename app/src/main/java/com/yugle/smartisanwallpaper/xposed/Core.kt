package com.yugle.smartisanwallpaper.xposed

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage

class Core : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        XposedBridge.log(".............${lpparam?.packageName}")
//        val timer = Timer()
//        val timerTask = object : TimerTask() {
//            override fun run() {
//                XposedBridge.log("Active..................")
//            }
//        }
//        timer.schedule(timerTask, 10000L, 10000L)
//        if (lpparam?.packageName == "tv.danmaku.bili") {
//            timer.cancel()
//        }
    }
}