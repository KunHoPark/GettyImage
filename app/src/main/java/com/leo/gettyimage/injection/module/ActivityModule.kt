package com.leo.gettyimage.injection.module;

import com.leo.gettyimage.injection.scope.ActivityScoped
import com.leo.gettyimage.ui.main.MainActivity
import com.leo.gettyimage.ui.main.MainModule
import com.leo.gettyimage.ui.splash.SplashActivity
import com.leo.gettyimage.ui.splash.SplashModule
import com.leo.gettyimage.ui.viewer.PhotoActivity
import com.leo.gettyimage.ui.viewer.PhotoModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(MainModule::class)])
    abstract fun bleMainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(SplashModule::class)])
    abstract fun splashActivity(): SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(PhotoModule::class)])
    abstract fun photoActivity(): PhotoActivity

}
