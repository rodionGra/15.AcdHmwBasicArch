package com.a15acdhmwbasicarch.domain

data class UserPostDomainModel(
        val userId: Int,
        val id: Int,
        val title: String,
        val body: String,
        val status: UserStatus
)