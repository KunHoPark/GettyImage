package com.leo.gettyimage.ui.viewer

import android.os.Bundle
import ccom.leo.gettyimage.ui.base.BaseActivity
import com.leo.gettyimage.R
import dagger.Lazy
import javax.inject.Inject

class PhotoActivity : BaseActivity() {

    @Inject
    lateinit var blePhotoFragmentProvider: Lazy<PhotoFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, blePhotoFragmentProvider.get())
                    .commitNow()
        }
    }

}
