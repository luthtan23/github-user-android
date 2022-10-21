package com.luthtan.github_user_android.data.datasource

import com.luthtan.github_user_android.data.dtos.HomeModel
import com.luthtan.github_user_android.data.dtos.entity.UserEntity
import com.luthtan.github_user_android.data.dtos.response.ReposResponse
import com.luthtan.github_user_android.data.dtos.response.ResponseApi
import com.luthtan.github_user_android.data.dtos.response.UserDetailResponse
import com.luthtan.github_user_android.data.dtos.response.UserResponse
import com.luthtan.github_user_android.data.local.BaseDao
import com.luthtan.github_user_android.data.remote.ApiService
import com.luthtan.github_user_android.domain.datasource.MyDataSource
import com.luthtan.github_user_android.domain.interactors.*
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Response
import javax.inject.Inject

class MyDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val baseDao: BaseDao
) : MyDataSource {
    override suspend fun getDataCoroutine(): Response<HomeModel> = apiService.getDataCoroutine()
    override fun getDataRxJava(): Flowable<HomeModel> = apiService.getDataRxJava()

    override suspend fun getUserList(param: GetUserList.Param): List<UserResponse> = apiService.getUserList(
        startPage = param.startPage,
        perPage = param.page
    )

    override suspend fun searchUser(param: SearchUser.Param): ResponseApi<List<UserResponse>> =
        apiService.searchUser(
            query = param.query,
            page = param.startPage,
            perPage = param.page
        )

    override suspend fun insertUserDao(param: InsertUserDao.Param): Long =
        baseDao.insertUser(param.userEntity)

    override suspend fun getUsers(): List<UserEntity> = baseDao.getUsers()

    override suspend fun deleteUser(param: DeleteUser.Param): Int = baseDao.deleteUser(param.userEntity.id)

    override suspend fun userDetail(param: UserDetail.Param): UserDetailResponse =
        apiService.userDetail(user = param.user)

    override suspend fun reposUser(param: ReposUser.Param): List<ReposResponse> =
        apiService.reposUser(param.user)

}