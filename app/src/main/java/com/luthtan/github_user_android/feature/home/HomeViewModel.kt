package com.luthtan.github_user_android.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.luthtan.github_user_android.base.BaseViewModel
import com.luthtan.github_user_android.base.ext.collectWithHandler
import com.luthtan.github_user_android.base.util.SingleEvents
import com.luthtan.github_user_android.data.dtos.entity.UserEntity
import com.luthtan.github_user_android.data.mapper.UserMapper
import com.luthtan.github_user_android.domain.interactors.*
import com.luthtan.github_user_android.domain.subscriber.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserList: GetUserList,
    private val insertUserDao: InsertUserDao,
    private val getUserListDao: GetUserListDao,
    private val deleteUser: DeleteUser,
    private val searchUser: SearchUser
) : BaseViewModel() {

    private val _users = MutableLiveData<ResultState<List<UserEntity>>>()
    val users: LiveData<ResultState<List<UserEntity>>> = _users

    private val _empty = MutableLiveData<SingleEvents<Boolean>>()
    val empty: LiveData<SingleEvents<Boolean>> = _empty

    val userTemp: MutableList<UserEntity> = mutableListOf()
    private val userLocal: MutableList<UserEntity> = mutableListOf()

    var pages: Int = INIT_PAGE

    fun initData() {
        viewModelScope.launch {
            if (userTemp.isEmpty()) {
                _users.value = ResultState.Loading()
            }
            // The since parameter says from which user ID the API should start listing users.
            val param = GetUserList.Param(
                if (userTemp.isNotEmpty()) userTemp.last().id else INIT_PAGE
            )
            getUserList.execute(param).collectWithHandler({
                it?.let { response ->
                    when(response.isEmpty()) {
                        true -> _empty.value = SingleEvents(true)
                        false -> {
                            val mapper = UserMapper()
                            userTemp.addAll(mapper.apply(response))
                            pages = userTemp.size
                            comparisonData()
                        }
                    }
                }
            }, errorObserver)
        }
    }

    fun localData() {
        viewModelScope.launch {
            userLocal.addAll(getUserListDao.build())
        }
    }

    private fun comparisonData() {
        viewModelScope.launch {
            val resetFav = userTemp.filter { it.isSelected }
            if (resetFav.isNotEmpty()) {
                resetFav.forEach {
                    val modelCopy = it
                    modelCopy.isSelected = false
                    userTemp[userTemp.indexOf(it)] = modelCopy
                }
            }
            userLocal.forEachIndexed { _, local ->
                val fav = userTemp.find { it.id == local.id }?.copy()
                if (fav != null) {
                    val modelCopy = fav.copy()
                    modelCopy.isSelected = true
                    userTemp[userTemp.indexOf(fav)] = modelCopy
                }
            }
            if (userTemp.isNotEmpty()) {
                _users.value = ResultState.Success(userTemp)
            }
        }
    }

    fun insertUser(userEntity: UserEntity, index: Int) {
        viewModelScope.launch {
            val param = InsertUserDao.Param(setSelectedLocal(userEntity))
            insertUserDao.execute(param).collectWithHandler({
                it?.let {
                    modelCopy(userEntity, index)
                }
            }, errorObserver)
        }
    }

    fun deleteUser(userEntity: UserEntity, index: Int) {
        viewModelScope.launch {
            val param = DeleteUser.Param(userEntity)
            deleteUser.execute(param).collectWithHandler({
                it?.let {
                    modelCopy(userEntity, index)
                }
            }, errorObserver)
        }
    }

    private fun modelCopy(userEntity: UserEntity, index: Int) {
        val modelCopy = userEntity.copy()
        modelCopy.isSelected = !userEntity.isSelected
        userTemp[index] = modelCopy
        _users.value = ResultState.Success(userTemp)
    }

    fun updateData() {
        viewModelScope.launch {
            userLocal.clear()
            userLocal.addAll(getUserListDao.build())
            comparisonData()
        }
    }

    // set true selected for insert local data
    private fun setSelectedLocal(userEntity: UserEntity): UserEntity {
        val modelCopy = userEntity.copy()
        modelCopy.isSelected = !userEntity.isSelected
        return modelCopy
    }

    fun searchUser(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                _users.value = ResultState.Success(userTemp)
                return@launch
            }
            if (userTemp.isEmpty()) {
                _users.value = ResultState.Loading()
            }
            // The since parameter says from which user ID the API should start listing users.
            val param = SearchUser.Param(
                query,
                if (userTemp.isNotEmpty()) pages else INIT_PAGE
            )
            searchUser.execute(param).collectWithHandler({
                it?.let { response ->
                    response.items?.let { model ->
                        when(model.isEmpty()) {
                            true -> _empty.value = SingleEvents(true)
                            false -> {
                                val mapper = UserMapper()
                                userTemp.addAll(mapper.apply(model))
                                comparisonData()
                            }
                        }
                    }
                }
            }, errorObserver)
        }
    }

    companion object {
        const val INIT_PAGE = 1
    }
}