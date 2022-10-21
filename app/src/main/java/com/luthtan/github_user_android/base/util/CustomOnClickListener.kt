package com.luthtan.github_user_android.base.util

abstract class CustomOnClickListener<T> {
    abstract fun onClickListener(data: T, index: Int)
}