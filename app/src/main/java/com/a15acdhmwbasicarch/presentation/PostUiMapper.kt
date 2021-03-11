package com.a15acdhmwbasicarch.presentation

import com.a15acdhmwbasicarch.R
import com.a15acdhmwbasicarch.data.AndroidResourceRepository
import com.a15acdhmwbasicarch.domain.model.UserPostDomainModel
import com.a15acdhmwbasicarch.domain.PostStatus
import javax.inject.Inject

class PostUiMapper @Inject constructor(
    private val resourceRepository: AndroidResourceRepository
) {

    fun map(domainListModel: List<UserPostDomainModel>): List<PostUiModel> {
        return domainListModel.let(this::getPostUiModels)
    }

    private fun getPostUiModels(userPostDomainModel: List<UserPostDomainModel>): List<PostUiModel> {
        return userPostDomainModel.map {
            when (it.postStatus) {
                PostStatus.STANDARD -> {
                    getStandardPostUiModel(it)
                }
                PostStatus.WITH_WARNING -> {
                    getStandardPostUiModel(it)
                }
                PostStatus.BANNED -> {
                    getUserPostUiModelBanned(it)
                }
            }
        }
    }

    private fun getStandardPostUiModel(userPostDomainModel: UserPostDomainModel): StandardPostUiModel {
        val (backgroundColor, hasWarning) = when (userPostDomainModel.postStatus) {
            PostStatus.WITH_WARNING -> Pair(
               resourceRepository.getColor(R.color.red),
                true
            )
            else -> Pair(
                resourceRepository.getColor(R.color.white),
                false
            )
        }

        return StandardPostUiModel(
            postId = userPostDomainModel.id,
            userId = userPostDomainModel.userId.toString(),
            title = userPostDomainModel.title,
            body = userPostDomainModel.body,
            hasWarning = hasWarning,
            backgroundColor = backgroundColor
        )
    }

    private fun getUserPostUiModelBanned(userPostDomainModel: UserPostDomainModel): BannedUserPostUiModel {
        return BannedUserPostUiModel(
            postId = userPostDomainModel.id,
            userId = userPostDomainModel.userId
        )
    }

}