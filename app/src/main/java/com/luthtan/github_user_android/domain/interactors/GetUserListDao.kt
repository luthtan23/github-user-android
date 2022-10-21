package com.luthtan.github_user_android.domain.interactors

import com.luthtan.github_user_android.data.dtos.entity.UserEntity
import com.luthtan.github_user_android.domain.repository.MyRepository

class GetUserListDao(
    private val repository: MyRepository
) {

    suspend fun build(): List<UserEntity> =
        repository.getUsers()

}