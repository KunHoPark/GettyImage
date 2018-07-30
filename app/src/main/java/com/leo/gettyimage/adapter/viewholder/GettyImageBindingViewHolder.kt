package com.leo.gettyimage.adapter.viewholder

import android.support.v7.widget.RecyclerView
import com.konai.cryptox.kotlin.extension.toImageUrlForThumbnail
import com.leo.gettyimage.BR
import com.leo.gettyimage.application.GettyImageApp
import com.leo.gettyimage.callback.OnItemClickListener
import com.leo.gettyimage.data.local.GettyImageEntity
import com.leo.gettyimage.databinding.ItemGettyImageViewHolderBinding
import com.leo.gettyimage.util.GlideImageUtils

class GettyImageBindingViewHolder(private var binding: ItemGettyImageViewHolderBinding, private val listener: OnItemClickListener?) : RecyclerView.ViewHolder(binding.root) {
    internal val tag = this.javaClass.simpleName

    fun onBind(any: Any, position: Int, size: Int) {
        val item = any as GettyImageEntity
        with(binding){

            this.setVariable(BR.gettyImageEntity, item)
            GlideImageUtils.loadImage(GettyImageApp.applicationContext(), item.thumbnailUrl.toImageUrlForThumbnail() , ivThumbnail)

            listener?.let {
                this.root.setOnClickListener {
                    listener.onItemClick(item as Object, it, position)
                }
            }
        }


    }
}