package com.a15acdhmwbasicarch.domain.model

import com.a15acdhmwbasicarch.datasource.model.AddedFrom
import com.a15acdhmwbasicarch.domain.Status

data class UserPostDomainModel(
        val userId: Int,
        val id: Int,
        val title: String,
        val body: String,
        val status: Status,
        val addedFrom: AddedFrom
)