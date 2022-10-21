package com.luthtan.github_user_android.data.remote

import com.luthtan.github_user_android.data.dtos.HomeModel
import com.luthtan.github_user_android.data.dtos.response.ReposResponse
import com.luthtan.github_user_android.data.dtos.response.ResponseApi
import com.luthtan.github_user_android.data.dtos.response.UserDetailResponse
import com.luthtan.github_user_android.data.dtos.response.UserResponse
import io.reactivex.rxjava3.core.Flowable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("sample/post/json")
    suspend fun getDataCoroutine(): Response<HomeModel>

    @POST("sample/post/json")
    fun getDataRxJava(): Flowable<HomeModel>

    @GET("users")
    suspend fun getUserList(
        @Query("since") startPage: Int,
        @Query("per_page") perPage: Int
    ): List<UserResponse>

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): ResponseApi<List<UserResponse>>

    @GET("users/{user}")
    suspend fun userDetail(
        @Path("user") user: String
    ): UserDetailResponse

    @GET("users/{user}/repos")
    suspend fun reposUser(
        @Path("user") user: String
    ): List<ReposResponse>

    /*@GET("3/discover/movie")
    fun getDataWithApiKey(
        @Query("api_key") apiKey: String,
        @QueryMap map: HashMap<String, Any>
    ): Flowable<HomeModel>

    @GET("3/movie/{movieId}/reviews")
    fun getDataWithPath(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): Flowable<HomeModel>*/
}