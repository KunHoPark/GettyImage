package ccom.leo.gettyimage.adapter

import android.databinding.BindingAdapter
import android.databinding.ObservableArrayList
import android.support.v7.widget.RecyclerView
import com.leo.gettyimage.data.local.GettyGalleryData
import com.leo.gettyimage.data.local.GettyImageEntity

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

}