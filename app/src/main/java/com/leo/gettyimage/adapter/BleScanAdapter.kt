package ccom.leo.gettyimage.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.leo.gettyimage.adapter.viewholder.BleScanBindingViewHolder
import com.leo.gettyimage.callback.OnItemClickListener
import com.leo.gettyimage.data.local.GettyGalleryData
import com.leo.gettyimage.databinding.ItemBleScanViewHolderBinding
import com.leo.gettyimage.util.LeoLog

/**
 * BleScanAdapter
 * @author KunHoPark
 * @since 2018. 7. 17. AM 10:43
 **/
class BleScanAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    internal val tag = this.javaClass.simpleName
    private var listener: OnItemClickListener? = null
    private var listData: List<GettyGalleryData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BleScanBindingViewHolder {
        val binding = ItemBleScanViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BleScanBindingViewHolder(binding, listener)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        listData?.let {
            val item = it[position]
            (holder as BleScanBindingViewHolder).onBind(item, position, it.size)
        }
    }

    fun addItems(any: List<Any>) {
        LeoLog.i(tag, "addItems size=${any.size}")

        val items: List<GettyGalleryData> = any as List<GettyGalleryData>
        listData = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData!!.size
    }

}