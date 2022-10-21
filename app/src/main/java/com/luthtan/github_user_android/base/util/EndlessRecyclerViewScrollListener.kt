package com.luthtan.github_user_android.base.util

import android.app.Activity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class EndlessRecyclerViewScrollListener(
    private val layoutManager: RecyclerView.LayoutManager
) : RecyclerView.OnScrollListener() {

    private var visibleThreshold = 5
    private var currentPage = 0
    var previousTotalItemCount = 0
    private var loading = true
    private val startingPageIndex = 0
    private var activity: Activity? = null

    init {
        if (layoutManager is StaggeredGridLayoutManager) {
            visibleThreshold *= layoutManager.spanCount
        } else if (layoutManager is GridLayoutManager) {
            visibleThreshold *= layoutManager.spanCount
        }
    }

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        if (activity != null) {
            KeyboardUtil.hideKeyboard(activity)
        }
        val lastVisibleItemPosition = getLastVisibleItem()
        val totalItemCount = layoutManager.itemCount

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            onLoadMore(totalItemCount)
            currentPage++
            loading = true
        }
    }

    private fun getLastVisibleItem(): Int {
        return when (layoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null)
                getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager -> {
                layoutManager.findLastVisibleItemPosition()
            }
            is LinearLayoutManager -> {
                layoutManager.findLastVisibleItemPosition()
            }
            else -> {
                0
            }
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    fun resetState() {
        this.currentPage = this.startingPageIndex
        this.previousTotalItemCount = 0
        this.loading = true
    }
    
    fun hideKeyboardWhenScrolling(activity: Activity) {
        this.activity = activity
    }

    abstract fun onLoadMore(pageNext: Int)

}