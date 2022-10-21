package com.luthtan.github_user_android.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luthtan.github_user_android.base.util.SingleEvents
import com.luthtan.github_user_android.data.dtos.ErrorModel

abstract class BaseViewModel : ViewModel() {
    fun onComplete() {
        // No-op by default
    }

    fun onDataEmpty() {
        // No-op by default
    }

    fun resume() {
        // No-op by default
    }

    fun pause() {
        // No-op by default
    }

    fun destroy() {
        // No-op by default
    }

    protected val _showToast = MutableLiveData<SingleEvents<String>>()
    val showToast: LiveData<SingleEvents<String>> = _showToast

    private val _errorApiHandler = MutableLiveData<SingleEvents<ErrorModel>>()
    val errorApiHandler: LiveData<SingleEvents<ErrorModel>> = _errorApiHandler

    val errorObserver: suspend (value: ErrorModel) -> Unit = { errorModel ->
        _errorApiHandler.postValue(SingleEvents(errorModel))
    }
}