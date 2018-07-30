package ccom.leo.gettyimage.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.leo.gettyimage.adapter.viewholder.GettyImageBindingViewHolder
import com.leo.gettyimage.callback.OnItemClickListener
import com.leo.gettyimage.data.local.GettyImageEntity
import com.leo.gettyimage.databinding.ItemGettyImageViewHolderBinding
import com.leo.gettyimage.util.LeoLog

/**
 * BleScanAdapter
 * @author KunHoPark
 * @since 2018. 7. 17. AM 10:43
 **/
class GettyImageAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    internal val tag = this.javaClass.simpleName
    private var listener: OnItemClickListener? = null
    private var listData: List<GettyImageEntity>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GettyImageBindingViewHolder {
        val binding = ItemGettyImageViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GettyImageBindingViewHolder(binding, listener)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        listData?.let {
            val item = it[position]
            (holder as GettyImageBindingViewHolder).onBind(item, position, it.size)
        }
    }

    fun addItems(any: List<Any>) {
        LeoLog.i(tag, "addItems size=${any.size}")

        val items: List<GettyImageEntity> = any as List<GettyImageEntity>
        listData = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData!!.size
    }

}