package com.leo.gettyimage.ui.ble

import android.os.Bundle
import ccom.leo.gettyimage.ui.base.BaseActivity
import com.leo.gettyimage.R
import dagger.Lazy
import javax.inject.Inject

/**
 * BleScanActivity
 * @author KunHoPark
 * @since 2018. 7. 17. AM 10:43
 **/
class BleScanActivity : BaseActivity() {

    @Inject
    lateinit var bleScanFragmentProvider: Lazy<BleScanFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ble_scan_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, bleScanFragmentProvider.get())
                    .commitNow()
        }

        title = "BLE"
    }

}
