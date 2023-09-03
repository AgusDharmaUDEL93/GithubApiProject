package com.udeldev.githubapiproject.providers.services

import com.udeldev.githubapiproject.models.data.UserDetailModel
import com.udeldev.githubapiproject.models.data.UserItemModel
import com.udeldev.githubapiproject.models.data.UserSearchModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getAllUser(): Call<List<UserItemModel>>

    @GET("search/users")
    fun getSearchUser(@Query("q") username : String) : Call<UserSearchModel>

    @GET ("users/{username}")
    fun getUserDetail( @Path("username") username: String) : Call<UserDetailModel>

    @GET ("users/{username}/followers")
    fun getUserFollower( @Path("username") username: String) : Call<List<UserItemModel>>

    @GET ("users/{username}/following")
    fun getUserFollowing( @Path("username") username: String) : Call<List<UserItemModel>>
}