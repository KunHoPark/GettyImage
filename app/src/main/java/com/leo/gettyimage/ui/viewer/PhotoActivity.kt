package com.leo.gettyimage.ui.viewer

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import ccom.leo.gettyimage.ui.base.BaseActivity
import com.leo.gettyimage.R
import com.leo.gettyimage.util.LeoLog
import dagger.Lazy
import javax.inject.Inject

class PhotoActivity : BaseActivity() {
    internal val tag = this.javaClass.simpleName

    @Inject
    lateinit var blePhotoFragmentProvider: Lazy<PhotoFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LeoLog.i(tag, "onCreate")

        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, blePhotoFragmentProvider.get())
                    .commitNow()
        }
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        super.onBackPressed()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        when(newConfig!!.orientation){
            Configuration.ORIENTATION_PORTRAIT -> {
                LeoLog.i(tag, "onConfigurationChanged ORIENTATION_PORTRAIT")
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                LeoLog.i(tag, "onConfigurationChanged ORIENTATION_PORTRAIT")
            }
        }

    }
}
