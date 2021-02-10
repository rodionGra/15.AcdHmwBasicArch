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
                UserStatus.WITH_WARNING -> {
                    getUserPostUiModelWithWarning(it)
                }
                UserStatus.BANNED -> {
                    getUserPostUiModelBanned(it)
                }
                UserStatus.STANDARD -> {
                    StandardPostUiModel(postId = it.id, userId = it.userId, title = it.title, body = it.body)
                }
            }
        }
    }

    private fun getUserPostUiModelWithWarning(userPostDomainModel: UserPostDomainModel): WarningUserPostUiModel {
        val backgroundColor = PostColors(resourceRepository.getColor(R.color.red))
        val warningText = resourceRepository.getString(R.string.warning)
        return WarningUserPostUiModel(
            postId = userPostDomainModel.id,
            userId = userPostDomainModel.userId,
            title = userPostDomainModel.title,
            body = userPostDomainModel.body,
            colors = backgroundColor,
            warningText = warningText
        )
    }

    private fun getUserPostUiModelBanned(userPostDomainModel: UserPostDomainModel): BannedUserPostUiModel {
        val banText = resourceRepository.getString(R.string.ban)
        val stringWithUserId: String = String.format(banText, userPostDomainModel.userId)
        return BannedUserPostUiModel(postId = userPostDomainModel.id, banText = stringWithUserId)
    }

}