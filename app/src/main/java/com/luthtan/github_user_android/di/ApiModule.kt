package com.luthtan.github_user_android.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.luthtan.github_user_android.MyApplication
import com.luthtan.github_user_android.base.config.Constants
import com.luthtan.github_user_android.base.config.WebApiProvider
import com.luthtan.github_user_android.base.util.SessionHelper
import com.luthtan.github_user_android.data.datasource.MyDataSourceImpl
import com.luthtan.github_user_android.data.local.BaseDao
import com.luthtan.github_user_android.data.local.BaseDatabaseDao
import com.luthtan.github_user_android.data.remote.ApiService
import com.luthtan.github_user_android.data.repository.MyRepositoryImpl
import com.luthtan.github_user_android.domain.datasource.MyDataSource
import com.luthtan.github_user_android.domain.repository.MyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideWebApiProvider(): WebApiProvider = WebApiProvider

    @Singleton
    @Provides
    fun provideOkhttpBuilder(
        myApplication: MyApplication
    ): OkHttpClient.Builder = OkHttpClient().newBuilder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .addInterceptor(ChuckerInterceptor.Builder(myApplication.applicationContext).build())
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(20, TimeUnit.SECONDS)

    @Singleton
    @Provides
    fun provideRetrofit(
        webApiProvider: WebApiProvider,
        myApplication: MyApplication,
        sessionHelper: SessionHelper,
        okHttpClientBuilder: OkHttpClient.Builder
    ): Retrofit = webApiProvider.getRetrofit(
        myApplication.getBaseUrl(),
        sessionHelper,
        okHttpClientBuilder
    )

    @Singleton
    @Provides
    fun provideMyDataSource(
        apiService: ApiService,
        baseDao: BaseDao
    ): MyDataSource =
        MyDataSourceImpl(apiService, baseDao)

    @Singleton
    @Provides
    fun provideMyRepository(source: MyDataSource): MyRepository =
        MyRepositoryImpl(source)

    @Singleton
    @Provides
    fun provideHome01Api(retrofit: Retrofit): ApiService = retrofit.create(
        ApiService::class.java
    )

    @Singleton
    @Provides
    fun provideTBMDatabase(
        @ApplicationContext context: Context
    ): BaseDatabaseDao = Room.databaseBuilder(
        context,
        BaseDatabaseDao::class.java,
        Constants.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideTbmDao(
        tbmDatabase: BaseDatabaseDao
    ): BaseDao = tbmDatabase.baseDao()
}