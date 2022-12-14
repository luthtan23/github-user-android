package com.luthtan.github_user_android

import com.luthtan.github_user_android.base.BaseApplication
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : BaseApplication() {

    // TODO add your baseUrl using buildConfig
    override fun getBaseUrl(): String = BuildConfig.API_BASE_URL

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}