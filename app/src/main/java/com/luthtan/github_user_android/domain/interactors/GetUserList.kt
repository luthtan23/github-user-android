package com.luthtan.github_user_android.domain.interactors

import com.luthtan.github_user_android.data.dtos.response.UserResponse
import com.luthtan.github_user_android.domain.baseusecase.coroutines.CoroutinesUseCase
import com.luthtan.github_user_android.domain.repository.MyRepository

class GetUserList(
    private val repository: MyRepository
) : CoroutinesUseCase<GetUserList.Param, List<UserResponse>>() {

    data class Param(
        val startPage: Int,
        val page: Int = 10
    )

    override suspend fun build(param: Param): List<UserResponse> =
        repository.getUserList(param)

}