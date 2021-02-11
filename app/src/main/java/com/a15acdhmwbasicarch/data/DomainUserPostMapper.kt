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
                val userStatusById = getStatusFromStatusSet(userPostResponse.userId)
                resultList.add(getUserPostDomainModel(userPostResponse, userStatusById))
            }
            resultList
        }
    }

    private fun getStatusFromStatusSet(userId: Int): UserStatus {
        return statusList.find { it.idUser == userId }?.status ?: UserStatus.STANDARD
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