package com.leo.gettyimage.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.kona.artmining.screen.custom.photoview.OnLoadImageListener
import com.leo.gettyimage.R
import com.leo.gettyimage.application.GettyImageApp
import java.io.File
import java.util.*


/**
 * GlideImageUtils
 * @author KunHo
 * @since 2018. 07. 30. PM 10:05
 **/
class GlideImageUtils {

    companion object {

        // for Network Images
        @JvmStatic fun loadImage(context: Context, imageUrl: String?, imageView: ImageView) {
            loadImage(context, imageUrl , imageView , Priority.NORMAL, null)
        }

        // drawable : placeholder drawable
        @JvmStatic fun loadImage(context: Context, imageUrl: String?, imageView: ImageView, drawable: Drawable?) {
            loadImage(context, imageUrl , imageView , Priority.NORMAL, drawable)
        }

        @JvmStatic fun loadImage(context: Context, imageUrl: String?, imageView: ImageView, priority: Priority?) {
            loadImage(context, imageUrl , imageView , priority, null)
        }

        @JvmStatic fun loadImage(context: Context, imageUrl: String?, imageView: ImageView, priority: Priority?, drawable: Drawable?) {
            var options : RequestOptions = getDefaultRequestOptions()
            LeoLog.i("GlideImageUtils", "loadImage imageUrl = $imageUrl")

            if(drawable != null) {
                options.apply {
                    placeholder(drawable)
                }
            }

            if(priority != null) {
                options.apply {
                    priority(priority)
                }
            }

            imageUrl?.let {
                Glide.with(context)
                        .applyDefaultRequestOptions(options)
                        .asBitmap().load(imageUrl)
                        .thumbnail(0.1f)
                        .apply(options)
                        .into(imageView)

            }
        }

        // for Local Images

        @JvmStatic fun loadImage(context: Context, resId: Int, imageView: ImageView) {
            //var width : Int  = pxToDp(imageView.width, context)
            //var height: Int = pxToDp(imageView.height, context)
            Glide.with(context)
                    .applyDefaultRequestOptions(getDefaultRequestOptions())
                    .asBitmap().load(resId)
                    .apply(getDefaultRequestOptions())
                    .into(imageView)
        }

        @JvmStatic fun loadImage(context: Context, file: File, imageView: ImageView) {
            //var width : Int  = pxToDp(imageView.width, context)
            //var height: Int = pxToDp(imageView.height, context)

            Glide.with(context)
                    .applyDefaultRequestOptions(getDefaultRequestOptions())
                    .asBitmap().load(file)
                    .apply(getDefaultRequestOptions())
                    .into(imageView)
        }


        @JvmStatic fun loadImageBitmap(context: Context, imageUrl: String?, imageView: ImageView, onLoadImageListener: OnLoadImageListener) {
            Glide.with(context)
                    .applyDefaultRequestOptions(getDefaultRequestOptions())
                    .asBitmap()
                    .load(imageUrl)
                    .apply(getDefaultRequestOptions())
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            imageView.setImageBitmap(resource)
                            onLoadImageListener.onLoad()
                        }
                    })
        }

        private fun getDefaultRequestOptions() : RequestOptions {
            return RequestOptions().apply {
                diskCacheStrategy(DiskCacheStrategy.ALL)
                format((DecodeFormat.PREFER_ARGB_8888))
                placeholder(getRandomPlaceHolderDrawable())
                error(getRandomPlaceHolderDrawable())
                dontAnimate()
            }
        }
        private fun getRandomPlaceHolderDrawable() : Drawable {
            val bgColors : Array<Int> = arrayOf(R.color.subgray, R.color.random_1, R.color.random_2)
            return ColorDrawable(ContextCompat.getColor(GettyImageApp.applicationContext(), bgColors[Random().nextInt(bgColors.size)]))
        }
    }

}
