package com.leo.gettyimage.ui.main

import android.content.res.Configuration
import android.os.Bundle
import ccom.leo.gettyimage.ui.base.BaseActivity
import com.leo.gettyimage.R
import com.leo.gettyimage.util.LeoLog
import dagger.Lazy
import javax.inject.Inject

class MainActivity : BaseActivity() {
    internal val tag = this.javaClass.simpleName

    @Inject
    lateinit var bleMainFragmentProvider: Lazy<MainFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, bleMainFragmentProvider.get())
                    .commitNow()
        }
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

    override fun onBackPressed() {
        backButtonClickSource.onNext(true)
    }

}
