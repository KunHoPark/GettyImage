package com.leo.gettyimage.application

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.facebook.stetho.Stetho
import com.leo.gettyimage.injection.component.AppComponent
import com.leo.gettyimage.injection.component.DaggerAppComponent
import dagger.android.support.DaggerApplication
import timber.log.Timber

class GettyImageApp : DaggerApplication() {
    companion object {
        val appComponent: AppComponent by lazy { DaggerAppComponent.builder()
                .application(GettyImageApp.instance!!)
                .build() }

        private var instance: GettyImageApp? = null
        @JvmStatic fun applicationContext() : Context = instance!!.applicationContext
        @JvmStatic fun application() : Application = instance!!

        fun resources() : Resources = instance!!.applicationContext.resources
    }

    override fun applicationInjector() = appComponent

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        Timber.uprootAll()
        Timber.plant(Timber.DebugTree())

        Stetho.initializeWithDefaults(this)
    }

}
