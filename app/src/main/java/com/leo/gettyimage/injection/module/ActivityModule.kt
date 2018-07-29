package com.leo.gettyimage.injection.module;

import com.leo.gettyimage.injection.scope.ActivityScoped
import com.leo.gettyimage.ui.blescan.BleScanModule
import com.leo.gettyimage.ui.main.BleScanActivity
import com.leo.gettyimage.ui.splash.SplashActivity
import com.leo.gettyimage.ui.splash.SplashModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityModule {


    @ActivityScoped
    @ContributesAndroidInjector(modules = [(BleScanModule::class)])
    abstract fun bleScanActivity(): BleScanActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(SplashModule::class)])
    abstract fun splashActivity(): SplashActivity


}
