package com.luthtan.github_user_android.data.dtos.response

import com.google.gson.annotations.SerializedName

data class ResponseApi<T>(
    @SerializedName("total_account")
    val totalAccount : Int? = null,

    @SerializedName("items")
    val items: T? = null
)
