package com.luthtan.github_user_android.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.luthtan.github_user_android.base.BaseViewModel
import com.luthtan.github_user_android.base.ext.collectWithHandler
import com.luthtan.github_user_android.base.util.SingleEvents
import com.luthtan.github_user_android.data.dtos.entity.UserEntity
import com.luthtan.github_user_android.data.dtos.response.ReposResponse
import com.luthtan.github_user_android.data.dtos.response.UserDetailResponse
import com.luthtan.github_user_android.domain.interactors.DeleteUser
import com.luthtan.github_user_android.domain.interactors.GetUserListDao
import com.luthtan.github_user_android.domain.interactors.ReposUser
import com.luthtan.github_user_android.domain.interactors.UserDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getUserListDao: GetUserListDao,
    private val deleteUser: DeleteUser,
    private val userDetail: UserDetail,
    private val reposUser: ReposUser
) : BaseViewModel() {

    private val _detail = MutableLiveData<UserDetailResponse>()
    val detail: LiveData<UserDetailResponse> = _detail

    private val _repos = MutableLiveData<List<ReposResponse>>()
    val repos: LiveData<List<ReposResponse>> = _repos

    private val _loading = MutableLiveData<SingleEvents<Boolean>>()
    val loading: LiveData<SingleEvents<Boolean>> = _loading

    fun initData(user: String) {
        viewModelScope.launch {
            _loading.value = SingleEvents(true)
            val param = UserDetail.Param(user)
            userDetail.execute(param).collectWithHandler({
                it?.let { response ->
                    _detail.value = response
                    repositories(user)
                }
            }, errorObserver)
        }
    }

    private fun repositories(user: String) {
        viewModelScope.launch {
            _loading.value = SingleEvents(false)
            val param = ReposUser.Param(user)
            reposUser.execute(param).collectWithHandler({
                it?.let { response ->
                    _repos.value = response
                }
            }, errorObserver)
        }
    }

    fun deleteUser(userEntity: UserEntity) {
        viewModelScope.launch {
//            val param = DeleteUser.Param(userEntity)
//            deleteUser.execute(param).collectWithHandler({
//                it?.let {
//                    userTemp.remove(userEntity)
//                    _users.value = userTemp
//                }
//            }, errorObserver)
        }
    }
}