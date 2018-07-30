package com.leo.gettyimage.ui.main

import com.leo.gettyimage.data.repository.GettyImageRepository
import com.leo.gettyimage.injection.scope.ActivityScoped
import com.leo.gettyimage.injection.scope.FragmentScoped
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun mainFragment(): MainFragment

    @Module
    companion object {
        @Provides
        @ActivityScoped
        @JvmStatic fun provideViewModelFactory(mainRepository: GettyImageRepository) : MainViewModelFactory
                = MainViewModelFactory(mainRepository)
    }

}
