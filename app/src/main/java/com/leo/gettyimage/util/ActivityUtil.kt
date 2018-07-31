package com.leo.gettyimage.util

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.leo.gettyimage.R
import com.leo.gettyimage.ui.main.MainActivity
import com.leo.gettyimage.ui.viewer.PhotoActivity

object ActivityUtil {

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
    fun startPhotoActivityForResult(fragment: Fragment, id: String, requestCode: Int) {
        Intent(fragment.context, PhotoActivity::class.java).run {
            putExtra(fragment.context!!.resources.getString(R.string.intent_action_key_id), id)
        }.also {
            fragment?.startActivityForResult(it, requestCode)
        }
    }

}