package com.leo.gettyimage.ui.main

import android.os.Bundle
import ccom.leo.gettyimage.ui.base.BaseActivity
import com.leo.gettyimage.R
import dagger.Lazy
import javax.inject.Inject

class MainActivity : BaseActivity() {

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

}
