package com.leo.gettyimage.util

import android.content.Context
import android.content.Intent
import com.leo.gettyimage.R
import com.leo.gettyimage.ui.ble.BleScanActivity
import com.leo.gettyimage.ui.main.MainActivity
import com.leo.gettyimage.ui.splash.SplashActivity
import com.leo.gettyimage.ui.viewer.PhotoActivity

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
     *  Main
     */
    fun startMainActivity(context: Context) {
        Intent(context, MainActivity::class.java).run {
            context?.startActivity(this)
        }
    }

    /**
     * Photo
     */
    fun startPhotoActivity(context: Context, id: String) {
        Intent(context, PhotoActivity::class.java).run {
            putExtra(context!!.resources.getString(R.string.intent_action_key_id), id)
        }.also {
            context?.startActivity(it)
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