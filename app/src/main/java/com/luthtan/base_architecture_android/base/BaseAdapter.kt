package com.luthtan.base_architecture_android.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.luthtan.base_architecture_android.databinding.ViewMoreLoadingBinding

@SuppressLint("NotifyDataSetChanged")
abstract class BaseAdapter<VB : ViewBinding, DATA>(
    private val inflate: Inflate<VB>,
) : RecyclerView.Adapter<BaseAdapter<VB, DATA>.BaseViewHolder>() {

    private val data: MutableList<DATA?> = mutableListOf()

    private var loadMore: Boolean = false

    open fun setData(data: List<DATA>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    open fun initLoadMore(isLoadMore: Boolean) {
        if (isLoadMore) {
            data.add(null)
            notifyItemInserted(data.size - 1)
        }
    }

    abstract fun bind(binding: VB, data: DATA?)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == VIEW_TYPE_DATA) {
            val binding = inflate.invoke(LayoutInflater.from(parent.context), parent, false)
            DataViewHolder(binding)
        } else {
            val view = ViewMoreLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadViewHolder(view.root)
        }
    }

    override fun getItemCount(): Int = data.size ?: 0

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder.javaClass.isAssignableFrom(DataViewHolder::class.java)) {
            bind(holder.getBinding(), data = data[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position] != null) {
            true -> VIEW_TYPE_DATA
            false -> VIEW_TYPE_LOADING
        }
    }

    inner class DataViewHolder(binding: VB) : BaseViewHolder(binding)

    inner class LoadViewHolder(view: View) : BaseViewHolder(view)

    open inner class BaseViewHolder : RecyclerView.ViewHolder {

        private var binding: VB? = null

        constructor(binding: VB) : super(binding.root) {
            this.binding = binding
        }

        constructor(view: View) : super(view)

        fun getBinding(): VB = binding!!
    }

    companion object {
        private const val VIEW_TYPE_DATA = 1
        private const val VIEW_TYPE_LOADING = 2
    }
}