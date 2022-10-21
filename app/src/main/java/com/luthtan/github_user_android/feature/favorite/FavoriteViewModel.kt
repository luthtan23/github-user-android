package com.luthtan.github_user_android.feature.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.luthtan.github_user_android.base.BaseViewModel
import com.luthtan.github_user_android.base.ext.collectWithHandler
import com.luthtan.github_user_android.base.util.SingleEvents
import com.luthtan.github_user_android.data.dtos.entity.UserEntity
import com.luthtan.github_user_android.domain.interactors.DeleteUser
import com.luthtan.github_user_android.domain.interactors.GetDataCoroutine
import com.luthtan.github_user_android.domain.interactors.GetDataRxJava
import com.luthtan.github_user_android.domain.interactors.GetUserListDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getUserListDao: GetUserListDao,
    private val deleteUser: DeleteUser
) : BaseViewModel() {

    private val _users = MutableLiveData<List<UserEntity>>()
    val users: LiveData<List<UserEntity>> = _users

    private val userTemp: MutableList<UserEntity> = mutableListOf()

    fun initData() {
        viewModelScope.launch {
            userTemp.clear()
            userTemp.addAll(getUserListDao.build())
            _users.value = userTemp
        }
    }

    fun deleteUser(userEntity: UserEntity) {
        viewModelScope.launch {
            val param = DeleteUser.Param(userEntity)
            deleteUser.execute(param).collectWithHandler({
                it?.let {
                    userTemp.remove(userEntity)
                    _users.value = userTemp
                }
            }, errorObserver)
        }
    }
}