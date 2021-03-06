package com.leo.gettyimage.util

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.leo.gettyimage.R
import com.leo.gettyimage.application.GettyImageApp
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.util.*


/**
 * PicassoUtils
 * @author KunHo
 * @since 2018. 07. 30. PM 10:05
 **/
class PicassoUtil {

    companion object {

        // 썹네일 이미지 출력
        @JvmStatic fun loadImage(context: Context, imageUrl: String?, imageView: ImageView) {
            Picasso.with(context)
                    .load(imageUrl)
                    .placeholder(getRandomPlaceHolderDrawable())
                    .error(getRandomPlaceHolderDrawable())
                    .into(imageView)
        }

        // 오리지널 이미지 출력.
        @JvmStatic fun loadImage(context: Context, thumbNailImageUrl: String?, originalImageUrl: String?, imageView: ImageView) {
            Picasso.with(context)
                    .load(thumbNailImageUrl)
                    .placeholder(getRandomPlaceHolderDrawable())
                    .error(getRandomPlaceHolderDrawable())
                    .into(imageView, object: Callback{
                        override fun onSuccess() {
                            Picasso.with(context)
                                    .load(originalImageUrl)
                                    .noPlaceholder()
                                    .error(getRandomPlaceHolderDrawable())
                                    .into(imageView)
                        }
                        override fun onError() {
                        }
                    })
        }

        private fun getRandomPlaceHolderDrawable() : Drawable {
            val bgColors : Array<Int> = arrayOf(R.color.subgray, R.color.random_1, R.color.random_2)
            return ColorDrawable(ContextCompat.getColor(GettyImageApp.applicationContext(), bgColors[Random().nextInt(bgColors.size)]))
        }
    }

}
