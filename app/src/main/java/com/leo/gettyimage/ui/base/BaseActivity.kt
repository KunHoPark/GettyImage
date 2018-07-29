package ccom.leo.gettyimage.ui.base

import android.os.Bundle
import android.widget.Toast
import com.leo.gettyimage.util.NetworkUtils
import dagger.android.support.DaggerAppCompatActivity


open abstract class BaseActivity: DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

    fun isNetworkAvailAble() : Boolean {
        return NetworkUtils.isNetworkAvailable(this)
    }

    fun showToast(title: String) {
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show()
    }


}