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

/**
 * RepositoryModule
 * AppComponent에 연결 된다.
 * @author KunHoPark
 * @since 2018. 7. 30. PM 2:07
 **/
@Module
abstract class ActivityModule {

    // ContributesAndroidInjector 어노테이션을 달고 반환 타입을 통해 해당 Activity의 Subcomponent를 생성 한다.
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
