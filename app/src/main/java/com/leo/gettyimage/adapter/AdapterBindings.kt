package ccom.leo.gettyimage.adapter

import android.databinding.BindingAdapter
import android.databinding.ObservableArrayList
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.konai.cryptox.kotlin.extension.toImageUrlForThumbnail
import com.leo.gettyimage.application.GettyImageApp
import com.leo.gettyimage.data.local.GettyGalleryData
import com.leo.gettyimage.data.local.GettyImageEntity
import com.leo.gettyimage.util.GlideImageUtils

object AdapterBindings {

    @BindingAdapter("bleDataItems")
    @JvmStatic
    fun setBleDataItems(recyclerView: RecyclerView, bleDatas: ObservableArrayList<GettyGalleryData>) {
        val adapter = recyclerView.adapter as BleScanAdapter
        if (adapter != null) {
            adapter!!.addItems(bleDatas)
        }
    }

    @BindingAdapter("gettyImageItems")
    @JvmStatic
    fun setGettyImageItems(recyclerView: RecyclerView, bleDatas: ObservableArrayList<GettyImageEntity>) {
        val adapter = recyclerView.adapter as GettyImageAdapter
        if (adapter != null) {
            adapter!!.addItems(bleDatas)
        }
    }

    @BindingAdapter("loadImage")
    @JvmStatic
    fun setLoadImage(imageView: ImageView, imageUrl: String) {
        GlideImageUtils.loadImage(GettyImageApp.applicationContext(), imageUrl.toImageUrlForThumbnail(), imageView)
    }

}