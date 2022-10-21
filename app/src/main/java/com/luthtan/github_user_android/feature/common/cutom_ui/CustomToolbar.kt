package com.luthtan.github_user_android.feature.common.cutom_ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.luthtan.github_user_android.R
import com.luthtan.github_user_android.databinding.ViewToolbarBinding

@SuppressLint("Recycle", "CustomViewStyleable")
class CustomToolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppBarLayout(context, attrs, defStyleAttr) {

    private var binding: ViewToolbarBinding =
        ViewToolbarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar)
        val titleText = typedArray.getString(R.styleable.CustomToolbar_titleText)
        val backgroundColor = typedArray.getColor(R.styleable.CustomToolbar_backgroundColorToolbar, ContextCompat.getColor(context, R.color.background_base_F2F5F7))
        val isShowProgress = typedArray.getBoolean(R.styleable.CustomToolbar_showProgress, false)
        val isSearch = typedArray.getBoolean(R.styleable.CustomToolbar_isSearch, false)
        val isHideBackButton = typedArray.getBoolean(R.styleable.CustomToolbar_hideBackButton, false)
        val cancelText = typedArray.getString(R.styleable.CustomToolbar_menuCancelText)
        val isShowCancelText = typedArray.getBoolean(R.styleable.CustomToolbar_showCancelText, false)
        val isTitleDefaultPosition = typedArray.getBoolean(R.styleable.CustomToolbar_isTitleDefaultPosition, false)
//        val isShowOptionMenu = typedArray.getBoolean(R.styleable.CustomToolbar_showOptionMenu, false)
        val progress = typedArray.getInt(R.styleable.CustomToolbar_progress, 0)
        val textColorOption = typedArray.getColor(R.styleable.CustomToolbar_textColorOption, ContextCompat.getColor(context, R.color.black))

        with(binding) {
            title.text = titleText

            menuCancel.apply {
                visibility = if (isShowCancelText) View.VISIBLE else View.GONE
                text = cancelText
                setTextColor(textColorOption)
            }

            progressBar.apply {
                visibility = if (isShowProgress) View.VISIBLE else View.GONE
                this.progress = progress
            }

            toolbarLyt.setBackgroundColor(backgroundColor)

            when(backgroundColor == ContextCompat.getColor(context, R.color.background_base_F2F5F7)) {
                true -> {
                    title.setTextColor(ContextCompat.getColor(context, R.color.black))
                    btnBack.setColorFilter(ContextCompat.getColor(context, R.color.black))
                }
                false -> {
                    title.setTextColor(ContextCompat.getColor(context, R.color.white))
                    btnBack.setColorFilter(ContextCompat.getColor(context, R.color.white))
                }
            }

            btnBack.visibility = if (isHideBackButton) View.GONE else View.VISIBLE

            btnSearch.visibility = if (isSearch) View.VISIBLE else View.GONE

            if (isTitleDefaultPosition) {
                val params = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.START
                }
                title.layoutParams = params
            }
        }
    }

    fun hideBackButton() {
        binding.btnBack.visibility = View.GONE
    }

    fun setOnBackButtonClick(onClickListener: (View) -> Unit) {
        binding.btnBack.setOnClickListener(onClickListener)
    }

    fun setOnSearchButtonClick(onClickListener: (View) -> Unit) {
        binding.btnSearch.setOnClickListener(onClickListener)
    }

    fun setOnMenuClick(onClickListener: (View) -> Unit) {
        binding.menuCancel.setOnClickListener(onClickListener)
    }

    fun setTitleToolbar(titleTxt: String) {
        binding.title.text = titleTxt
    }

    fun setImageClose() {
//        binding.btnBack.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_close))
    }

    fun setCenterTitle() {
        with(binding) {
            val params = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
            title.layoutParams = params
        }
    }

    fun showBackButton() {
        binding.btnBack.visibility = View.VISIBLE
    }
}