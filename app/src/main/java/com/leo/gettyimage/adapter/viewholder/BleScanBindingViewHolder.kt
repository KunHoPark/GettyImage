package com.leo.gettyimage.adapter.viewholder

import android.support.v7.widget.RecyclerView
import com.leo.gettyimage.BR
import com.leo.gettyimage.callback.OnItemClickListener
import com.leo.gettyimage.data.local.GettyGalleryData
import com.leo.gettyimage.databinding.ItemBleScanViewHolderBinding

class BleScanBindingViewHolder(private var binding: ItemBleScanViewHolderBinding, private val listener: OnItemClickListener?) : RecyclerView.ViewHolder(binding.root) {
    internal val tag = this.javaClass.simpleName

    fun onBind(any: Any, position: Int, size: Int) {
        val bleData = any as GettyGalleryData
        with(binding){

            this.setVariable(BR.bleData, bleData)
            bleData?.let {
//                this.tvMac.text = it.id
//                this.tvName.text = it.name
            }

            listener?.let {

            }
        }


    }
}