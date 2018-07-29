package com.leo.gettyimage.util

import android.content.Context
import android.content.Intent
import com.leo.gettyimage.ui.main.BleScanActivity
import com.leo.gettyimage.ui.splash.SplashActivity

object ActivityUtil {

    /**
     *  Splash
     */
    fun startSplashActivity(context: Context) {
        Intent(context, SplashActivity::class.java).run {
            context?.startActivity(this)
        }
    }

    /**
     *  Ble Scan
     */
    fun startBleScanActivity(context: Context) {
        Intent(context, BleScanActivity::class.java).run {
            context?.startActivity(this)
        }
    }


}