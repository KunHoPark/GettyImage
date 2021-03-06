package ccom.leo.gettyimage.ui.base

import android.os.Bundle
import android.widget.Toast
import com.leo.gettyimage.R
import com.leo.gettyimage.extension.plusAssign
import com.leo.gettyimage.util.AutoClearedDisposable
import com.leo.gettyimage.util.NetworkUtils
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


open abstract class BaseActivity: DaggerAppCompatActivity() {

    internal var viewDisposables =  AutoClearedDisposable(lifecycleOwner = this, alwaysClearOnStop = false)
    protected val backButtonClickSource = PublishSubject.create<Boolean>()!!

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        lifecycle += viewDisposables
    }

    override fun onResume() {
        super.onResume()
        //2회 back key 호출 하면 종료
        viewDisposables.add(backButtonClickSource
                .debounce(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { showToast(getString(R.string.common_app_exit_msg)) }
                .timeInterval(TimeUnit.MILLISECONDS)
                .skip(1)
                .filter { interval -> interval.time() < 2000 }
                .subscribe {
                    finishAffinity()
                })
    }

    fun isNetworkAvailAble() : Boolean {
        return NetworkUtils.isNetworkAvailable(this)
    }

    fun showToast(title: String) {
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show()
    }


}