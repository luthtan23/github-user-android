package com.luthtan.github_user_android.domain.interactors

import com.luthtan.github_user_android.data.dtos.entity.UserEntity
import com.luthtan.github_user_android.domain.baseusecase.coroutines.CoroutinesUseCase
import com.luthtan.github_user_android.domain.repository.MyRepository

class DeleteUser(
    private val repository: MyRepository
) : CoroutinesUseCase<DeleteUser.Param, Int>() {

    data class Param(
        val userEntity: UserEntity
    )

    override suspend fun build(param: Param): Int =
        repository.deleteUser(param)

}