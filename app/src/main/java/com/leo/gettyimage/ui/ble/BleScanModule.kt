package com.leo.gettyimage.ui.blescan

import com.leo.gettyimage.data.repository.GettyGalleryRepository
import com.leo.gettyimage.injection.scope.ActivityScoped
import com.leo.gettyimage.injection.scope.FragmentScoped
import com.leo.gettyimage.ui.ble.BleScanFragment
import com.leo.gettyimage.ui.ble.BleScanViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

/**
 * BleScanModule
 * @author KunHoPark
 * @since 2018. 7. 17. AM 10:43
 **/
@Module
abstract class BleScanModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun bleScanFragment(): BleScanFragment

    @Module
    companion object {
        @Provides
        @ActivityScoped
        @JvmStatic fun provideViewModelFactory(mainRepository: GettyGalleryRepository) : BleScanViewModelFactory
                = BleScanViewModelFactory(mainRepository)
    }

}
