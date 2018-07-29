package com.leo.gettyimage.ui.splash

import com.leo.gettyimage.data.repository.GettyImageRepository
import com.leo.gettyimage.injection.scope.ActivityScoped
import com.leo.gettyimage.injection.scope.FragmentScoped
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class SplashModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun splashFragment(): SplashFragment

    @Module
    companion object {
        @Provides
        @ActivityScoped
        @JvmStatic fun provideViewModelFactory(mainRepository: GettyImageRepository) : SplashViewModelFactory
                = SplashViewModelFactory(mainRepository)
    }

}
