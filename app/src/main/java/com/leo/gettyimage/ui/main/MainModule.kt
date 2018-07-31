package com.leo.gettyimage.ui.main

import com.leo.gettyimage.data.repository.GettyImageRepository
import com.leo.gettyimage.injection.scope.ActivityScoped
import com.leo.gettyimage.injection.scope.FragmentScoped
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    // ContributesAndroidInjector로 FragmentSubcomponent를 생성한다.
    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun mainFragment(): MainFragment

    // ViewModelFactory 타입의 의존성 객체를 생성 한다. ViwModel를 생성 해 준다.
    @Module
    companion object {
        @Provides
        @ActivityScoped
        @JvmStatic fun provideViewModelFactory(mainRepository: GettyImageRepository) : MainViewModelFactory
                = MainViewModelFactory(mainRepository)
    }

}
