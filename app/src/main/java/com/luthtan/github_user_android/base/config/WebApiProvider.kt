package com.luthtan.github_user_android.base.config

import android.content.Context
import com.luthtan.github_user_android.base.util.SessionHelper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object WebApiProvider {
    fun getRetrofit(
        url: String,
        sessionHelper: SessionHelper,
        okHttpClientBuilder: OkHttpClient.Builder
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(
            okHttpClientBuilder
                .addInterceptor(
                    OAuthInterceptor(
                        OAuthInterceptor.BEARER,
                        sessionHelper
                    )
                )
                .build()
        )
        .build()
}