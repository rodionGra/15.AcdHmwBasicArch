package com.a15acdhmwbasicarch.presentation

import com.a15acdhmwbasicarch.R
import com.a15acdhmwbasicarch.ResourceRepository
import com.a15acdhmwbasicarch.domain.UserPostDomainModel
import com.a15acdhmwbasicarch.domain.UserStatus

class PostUiMapper(private val resourceRepository: ResourceRepository) {
    fun map(domainListModel: List<UserPostDomainModel>?): List<PostUiModel>? {
        return domainListModel?.let(this::getPostUiModels)
    }

    private fun getPostUiModels(userPostDomainModel: List<UserPostDomainModel>): List<PostUiModel> {
        return userPostDomainModel.map {
            when (it.status) {
                UserStatus.STANDARD -> {
                    getStandardPostUiModel(it)
                }
                UserStatus.WITH_WARNING -> {
                    getStandardPostUiModel(it)
                }
                UserStatus.BANNED -> {
                    getUserPostUiModelBanned(it)
                }
            }
        }
    }

    private fun getStandardPostUiModel(userPostDomainModel: UserPostDomainModel): StandardPostUiModel {
        val (backgroundColor, hasWarning) = when (userPostDomainModel.status) {
            UserStatus.WITH_WARNING -> Pair(
                PostColors(resourceRepository.getColor(R.color.red)),
                true
            )
            else -> Pair(
                PostColors(resourceRepository.getColor(R.color.white)),
                false
            )
        }

        return StandardPostUiModel(
            postId = userPostDomainModel.id,
            userId = userPostDomainModel.userId.toString(),
            title = userPostDomainModel.title,
            body = userPostDomainModel.body,
            hasWarning = hasWarning,
            colors = backgroundColor
        )
    }

    private fun getUserPostUiModelBanned(userPostDomainModel: UserPostDomainModel): BannedUserPostUiModel {
        return BannedUserPostUiModel(
            postId = userPostDomainModel.id,
            userId = userPostDomainModel.userId
        )
    }

}