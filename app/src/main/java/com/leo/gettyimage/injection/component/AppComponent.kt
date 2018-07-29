package com.leo.gettyimage.injection.component;


import android.app.Application
import com.leo.gettyimage.application.GettyImageApp
import com.leo.gettyimage.injection.module.*
import com.leo.gettyimage.ui.main.BleScanViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    (AppModule::class),
    (ActivityModule::class),
    (NetworkDataModule::class),
    (LocalDataModule::class),
    (RepositoryModule::class),
    (AndroidSupportInjectionModule::class)])

/**
* AppComponent Interface
* @author JungWoongLee
* @since 2018. 6. 8. AM 10:03
**/
interface AppComponent : AndroidInjector<GettyImageApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder
        fun build(): AppComponent
    }

    override fun inject(instance: GettyImageApp)

    /**
     *  ViewModels
     */
    fun inject(bleScanViewModel: BleScanViewModel)



}
