package com.luthtan.github_user_android.domain.interactors

import com.luthtan.github_user_android.data.dtos.response.UserDetailResponse
import com.luthtan.github_user_android.domain.baseusecase.coroutines.CoroutinesUseCase
import com.luthtan.github_user_android.domain.repository.MyRepository

class UserDetail(
    private val repository: MyRepository
) : CoroutinesUseCase<UserDetail.Param, UserDetailResponse>() {

    data class Param(
        val user: String
    )

    override suspend fun build(param: Param): UserDetailResponse =
        repository.userDetail(param)

}