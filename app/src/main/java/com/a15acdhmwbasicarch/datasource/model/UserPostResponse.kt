package com.a15acdhmwbasicarch.datasource.model

import com.google.gson.annotations.SerializedName

data class UserPostResponse(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String
)