package com.luthtan.github_user_android.base

import android.app.Application

abstract class BaseApplication : Application() {
    abstract fun getBaseUrl(): String
}