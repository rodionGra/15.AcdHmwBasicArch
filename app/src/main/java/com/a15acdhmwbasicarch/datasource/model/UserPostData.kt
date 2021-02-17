package com.a15acdhmwbasicarch.datasource.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class UserPostResponse(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("id")
    @PrimaryKey
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String
)