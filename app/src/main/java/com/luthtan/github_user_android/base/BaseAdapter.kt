package com.luthtan.github_user_android.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.luthtan.github_user_android.base.util.CustomOnClickListener
import com.luthtan.github_user_android.databinding.ItemErrorLoadMoreBinding
import com.luthtan.github_user_android.databinding.ViewMoreLoadingBinding

@SuppressLint("NotifyDataSetChanged")
abstract class BaseAdapter<VB : ViewBinding, DATA>(
    private val inflate: Inflate<VB>,
) : RecyclerView.Adapter<BaseAdapter<VB, DATA>.BaseViewHolder>() {

    private var onClickErrorLoadMore: OnClickErrorLoadMore? = null

    private val data: MutableList<DATA?> = mutableListOf()

    private var errorLoadMore: Boolean = false
    var isLoadMore: Boolean = false

    protected var customOnClickListener: CustomOnClickListener<DATA>? = null

    fun setOnClickListener(customOnClickListener: CustomOnClickListener<DATA>) {
        this.customOnClickListener = customOnClickListener
    }

    open fun setData(data: List<DATA>) {
        this.isLoadMore = false
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    open fun initLoadMore(isLoadMore: Boolean) {
        this.isLoadMore = isLoadMore
        if (isLoadMore) {
            errorLoadMore = false
            data.add(null)
            notifyItemInserted(data.size - 1);
        }
    }

    open fun initHandleErrorLoadMore(isErrorLoadMore: Boolean) {
        // TODO don't add notifyItemInsert, this method will replace initLoadMore item
        this.errorLoadMore = isErrorLoadMore
    }

    open fun setHandleErrorLoadMore(onClickErrorLoadMore: OnClickErrorLoadMore) {
        this.onClickErrorLoadMore = onClickErrorLoadMore
    }

    abstract fun bind(binding: VB, data: DATA?, index: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_DATA -> {
                val binding = inflate.invoke(LayoutInflater.from(parent.context), parent, false)
                DataViewHolder(binding)
            }
            VIEW_TYPE_LOADING -> {
                val view = ViewMoreLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                LoadViewHolder(view.root)
            }
            else -> {
                val view = ItemErrorLoadMoreBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ErrorViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder.javaClass.isAssignableFrom(DataViewHolder::class.java)) {
            bind(holder.getBinding(), data = data[position], position)
        } else if (holder.javaClass.isAssignableFrom(ErrorViewHolder::class.java)) {
            holder.getErrorBinding().btnError.setOnClickListener {
                onClickErrorLoadMore?.onClickHandle()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position] != null) {
            true -> VIEW_TYPE_DATA
            false -> {
                // TODO errorHandle will replace loadMoreLoading
                if (errorLoadMore) {
                    VIEW_TYPE_ERROR
                } else {
                    VIEW_TYPE_LOADING
                }
            }
        }
    }

    inner class DataViewHolder(binding: VB) : BaseViewHolder(binding)

    inner class LoadViewHolder(view: View) : BaseViewHolder(view)

    inner class ErrorViewHolder(view: ItemErrorLoadMoreBinding) : BaseViewHolder(view)

    open inner class BaseViewHolder : RecyclerView.ViewHolder {

        private var binding: VB? = null
        private var errorBinding: ItemErrorLoadMoreBinding? = null

        constructor(binding: VB) : super(binding.root) {
            this.binding = binding
        }

        constructor(view: View) : super(view)

        constructor(errorBinding: ItemErrorLoadMoreBinding) : super(errorBinding.root) {
            this.errorBinding = errorBinding
        }

        fun getBinding(): VB = binding!!

        fun getErrorBinding(): ItemErrorLoadMoreBinding = errorBinding!!
    }

    interface OnClickErrorLoadMore {
        fun onClickHandle()
    }

    companion object {
        private const val VIEW_TYPE_DATA = 1
        private const val VIEW_TYPE_LOADING = 2
        private const val VIEW_TYPE_ERROR = 3
    }
}