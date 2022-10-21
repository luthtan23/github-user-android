package com.luthtan.github_user_android.domain.interactors

import com.luthtan.github_user_android.data.dtos.HomeModel
import com.luthtan.github_user_android.domain.baseusecase.coroutines.CoroutinesUseCase
import com.luthtan.github_user_android.domain.repository.MyRepository
import com.luthtan.github_user_android.domain.subscriber.ResultState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response

class GetDataCoroutine(private val repository: MyRepository) : CoroutinesUseCase<GetDataCoroutine.Param, Response<HomeModel>>() {
    override suspend fun build(param: Param): Response<HomeModel> = repository.getDataCoroutine()

    fun execute(coroutineScope: CoroutineScope, param: Param, onResponse: (data: ResultState<Response<HomeModel>?>) -> Unit) {
        coroutineScope.launch {
            execute(param).collect {
                onResponse.invoke(it)
            }
        }
    }

    class Param {

    }
}