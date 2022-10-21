package com.luthtan.github_user_android.domain.interactors

import com.luthtan.github_user_android.data.dtos.response.ReposResponse
import com.luthtan.github_user_android.domain.baseusecase.coroutines.CoroutinesUseCase
import com.luthtan.github_user_android.domain.repository.MyRepository

class ReposUser(
    private val repository: MyRepository
) : CoroutinesUseCase<ReposUser.Param, List<ReposResponse>>() {

    data class Param(
        val user: String
    )

    override suspend fun build(param: Param): List<ReposResponse> =
        repository.reposUser(param)

}