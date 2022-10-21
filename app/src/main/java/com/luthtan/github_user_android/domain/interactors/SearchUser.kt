package com.luthtan.github_user_android.domain.interactors

import com.luthtan.github_user_android.data.dtos.response.ResponseApi
import com.luthtan.github_user_android.data.dtos.response.UserResponse
import com.luthtan.github_user_android.domain.baseusecase.coroutines.CoroutinesUseCase
import com.luthtan.github_user_android.domain.repository.MyRepository

class SearchUser(
    private val repository: MyRepository
) : CoroutinesUseCase<SearchUser.Param,ResponseApi<List<UserResponse>>>() {

    data class Param(
        val query: String,
        val startPage: Int,
        val page: Int = 10
    )

    override suspend fun build(param: Param): ResponseApi<List<UserResponse>> =
        repository.searchUser(param)

}