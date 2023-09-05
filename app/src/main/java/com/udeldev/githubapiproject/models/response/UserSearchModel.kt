package com.udeldev.githubapiproject.models.response

import com.google.gson.annotations.SerializedName

data class UserSearchModel(

    @field:SerializedName("total_count")
    val totalCount: Int? = null,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,

    @field:SerializedName("items")
    val items: List<UserItemModel>? = null
)


