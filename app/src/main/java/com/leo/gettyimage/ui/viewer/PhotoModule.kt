package com.leo.gettyimage.ui.viewer

import com.leo.gettyimage.R
import com.leo.gettyimage.data.repository.GettyImageRepository
import com.leo.gettyimage.data.repository.ImageDetailRepository
import com.leo.gettyimage.injection.scope.ActivityScoped
import com.leo.gettyimage.injection.scope.FragmentScoped
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class PhotoModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun photoFragment(): PhotoFragment

    @Module
    companion object {

//        @Provides
//        @ActivityScoped
//        @JvmStatic fun provideImageId(activity: PhotoActivity): String =
//                activity.intent.extras?.run{
//                    getString(activity.getString(R.string.intent_action_key_id))
//                } ?: ""


        @Provides
        @ActivityScoped
        @JvmStatic fun provideViewModelFactory(activity: PhotoActivity, imageDetailRepository: ImageDetailRepository) : PhotoViewModelFactory {
            val imageId = activity.intent.extras?.run{ getString(activity.getString(R.string.intent_action_key_id)) }
            return PhotoViewModelFactory(imageDetailRepository, imageId!!)
        }

    }

}
