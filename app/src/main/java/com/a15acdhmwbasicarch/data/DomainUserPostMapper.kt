package com.a15acdhmwbasicarch.data

import com.a15acdhmwbasicarch.domain.UserPostDomainModel
import com.a15acdhmwbasicarch.domain.UserStatus

class DomainUserPostMapper(private val statusList: Set<StatusUser>) {
    fun map(
        postResponse: List<UserPostResponse>?,
    ): List<UserPostDomainModel>? {
        return postResponse?.let {
            val resultList = mutableListOf<UserPostDomainModel>()
            postResponse.forEach { userPostResponse ->
                if (statusList.any { it.idUser == userPostResponse.userId }) {
                    val status = statusList.find { it.idUser == userPostResponse.userId }?.status
                        ?: UserStatus.STANDARD
                    resultList.add(
                        getUserPostDomainModel(userPostResponse, status)
                    )
                } else {
                    resultList.add(getUserPostDomainModel(userPostResponse))
                }
            }
            resultList
        }
    }

    private fun getUserPostDomainModel(
        userPostResponse: UserPostResponse,
        status: UserStatus = UserStatus.STANDARD
    ): UserPostDomainModel {
        return UserPostDomainModel(
            userId = userPostResponse.userId,
            id = userPostResponse.id,
            title = userPostResponse.title,
            body = userPostResponse.body,
            status = status
        )
    }
}