package com.udeldev.githubapiproject.models.response

import com.google.gson.annotations.SerializedName

data class UserItemModel(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("login")
    val githubId: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

)
