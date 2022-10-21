package com.luthtan.github_user_android.domain.interactors

import com.luthtan.github_user_android.data.dtos.entity.UserEntity
import com.luthtan.github_user_android.domain.baseusecase.coroutines.CoroutinesUseCase
import com.luthtan.github_user_android.domain.repository.MyRepository

class InsertUserDao(
    private val repository: MyRepository
) : CoroutinesUseCase<InsertUserDao.Param, Long>() {

    data class Param(
        val userEntity: UserEntity
    )

    override suspend fun build(param: Param): Long =
        repository.insertUserDao(param)

}