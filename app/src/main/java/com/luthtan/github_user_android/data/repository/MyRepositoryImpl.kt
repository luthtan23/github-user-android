package com.luthtan.github_user_android.data.repository

import com.luthtan.github_user_android.data.dtos.HomeModel
import com.luthtan.github_user_android.data.dtos.entity.UserEntity
import com.luthtan.github_user_android.data.dtos.response.ReposResponse
import com.luthtan.github_user_android.data.dtos.response.ResponseApi
import com.luthtan.github_user_android.data.dtos.response.UserDetailResponse
import com.luthtan.github_user_android.data.dtos.response.UserResponse
import com.luthtan.github_user_android.domain.datasource.MyDataSource
import com.luthtan.github_user_android.domain.interactors.*
import com.luthtan.github_user_android.domain.repository.MyRepository
import io.reactivex.rxjava3.core.Flowable
import retrofit2.Response
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val myDataSource: MyDataSource
) : MyRepository {

    override suspend fun getDataCoroutine(): Response<HomeModel> = myDataSource.getDataCoroutine()
    override fun getDataRxJava(): Flowable<HomeModel> = myDataSource.getDataRxJava()

    override suspend fun getUserList(param: GetUserList.Param): List<UserResponse> = myDataSource.getUserList(param)

    override suspend fun searchUser(param: SearchUser.Param): ResponseApi<List<UserResponse>> =
        myDataSource.searchUser(param)

    override suspend fun insertUserDao(param: InsertUserDao.Param): Long =
        myDataSource.insertUserDao(param)

    override suspend fun getUsers(): List<UserEntity> = myDataSource.getUsers()

    override suspend fun deleteUser(param: DeleteUser.Param): Int = myDataSource.deleteUser(param)

    override suspend fun userDetail(param: UserDetail.Param): UserDetailResponse =
        myDataSource.userDetail(param)

    override suspend fun reposUser(param: ReposUser.Param): List<ReposResponse> =
        myDataSource.reposUser(param)

}