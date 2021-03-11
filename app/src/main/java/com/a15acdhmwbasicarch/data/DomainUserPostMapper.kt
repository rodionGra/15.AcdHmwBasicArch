package com.a15acdhmwbasicarch.data

import com.a15acdhmwbasicarch.datasource.UserStatusLocalDataSource
import com.a15acdhmwbasicarch.datasource.model.UserPostData
import com.a15acdhmwbasicarch.domain.model.UserPostDomainModel
import com.a15acdhmwbasicarch.domain.PostStatus
import javax.inject.Inject

class DomainUserPostMapper @Inject constructor(
    private val statusList: UserStatusLocalDataSource
) {

    fun map(
        postData: List<UserPostData>,
    ): List<UserPostDomainModel> {
        return postData.let {
            it.map { userPostResponse ->
                val userStatusById = getStatusFromStatusSet(userPostResponse.userId)
                getUserPostDomainModel(userPostResponse, userStatusById)
            }
        }
    }

    private fun getStatusFromStatusSet(userId: Int): PostStatus {
        return statusList.getSetOfStatusUser().find { it.idUser == userId }?.postStatus
            ?: PostStatus.STANDARD
    }

    private fun getUserPostDomainModel(
        userPostData: UserPostData,
        postStatus: PostStatus
    ): UserPostDomainModel {
        return UserPostDomainModel(
            userId = userPostData.userId,
            id = userPostData.id,
            title = userPostData.title,
            body = userPostData.body,
            postStatus = postStatus,
            addedFrom = userPostData.addedFrom
        )
    }
}