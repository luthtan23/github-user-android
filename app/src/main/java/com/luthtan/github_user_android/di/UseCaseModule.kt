package com.luthtan.github_user_android.di

import com.luthtan.github_user_android.domain.PostExecutionThread
import com.luthtan.github_user_android.domain.interactors.*
import com.luthtan.github_user_android.domain.repository.MyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetDataCoroutine(
        repository: MyRepository
    ): GetDataCoroutine = GetDataCoroutine(repository)

    @Provides
    @ViewModelScoped
    fun provideGetDataRxJava(
        repository: MyRepository,
        postExecutionThread: PostExecutionThread
    ): GetDataRxJava = GetDataRxJava(repository, postExecutionThread)

    @Provides
    @ViewModelScoped
    fun provideGetUserList(
        repository: MyRepository
    ): GetUserList = GetUserList(repository)

    @Provides
    @ViewModelScoped
    fun provideInsertUserDao(
        repository: MyRepository
    ): InsertUserDao = InsertUserDao(repository)

    @Provides
    @ViewModelScoped
    fun provideGetUserListDao(
        repository: MyRepository
    ): GetUserListDao = GetUserListDao(repository)

    @Provides
    @ViewModelScoped
    fun provideDeleteUser(
        repository: MyRepository
    ): DeleteUser = DeleteUser(repository)

    @Provides
    @ViewModelScoped
    fun provideSearchUser(
        repository: MyRepository
    ): SearchUser = SearchUser(repository)

    @Provides
    @ViewModelScoped
    fun provideUserDetail(
        repository: MyRepository
    ): UserDetail = UserDetail(repository)

    @Provides
    @ViewModelScoped
    fun provideReposUser(
        repository: MyRepository
    ): ReposUser = ReposUser(repository)
}