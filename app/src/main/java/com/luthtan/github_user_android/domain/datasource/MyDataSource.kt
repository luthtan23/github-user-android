package com.luthtan.github_user_android.domain.datasource

import com.luthtan.github_user_android.data.dtos.HomeModel
import com.luthtan.github_user_android.data.dtos.entity.UserEntity
import com.luthtan.github_user_android.data.dtos.response.ReposResponse
import com.luthtan.github_user_android.data.dtos.response.ResponseApi
import com.luthtan.github_user_android.data.dtos.response.UserDetailResponse
import com.luthtan.github_user_android.data.dtos.response.UserResponse
import com.luthtan.github_user_android.domain.interactors.*
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MyDataSource {

    suspend fun getDataCoroutine(): Response<HomeModel>
    fun getDataRxJava(): Flowable<HomeModel>

    suspend fun getUserList(param: GetUserList.Param): List<UserResponse>

    suspend fun searchUser(param: SearchUser.Param): ResponseApi<List<UserResponse>>

    suspend fun insertUserDao(param: InsertUserDao.Param): Long

    suspend fun getUsers(): List<UserEntity>

    suspend fun deleteUser(param: DeleteUser.Param): Int

    suspend fun userDetail(param: UserDetail.Param): UserDetailResponse

    suspend fun reposUser(param: ReposUser.Param): List<ReposResponse>

}