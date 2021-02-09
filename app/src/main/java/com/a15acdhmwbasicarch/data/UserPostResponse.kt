package com.a15acdhmwbasicarch.data

import com.google.gson.annotations.SerializedName

//TODO  add "@Expose"  if doesnt work

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