package com.a15acdhmwbasicarch.presentation

import android.graphics.Color
import com.a15acdhmwbasicarch.Result
import com.a15acdhmwbasicarch.UserPostError
import com.a15acdhmwbasicarch.domain.UserPostDomainModel
import com.a15acdhmwbasicarch.domain.UserStatus

class PostUiMapper {
    fun map(domainListModel: List<UserPostDomainModel>?): List<PostUiModel>? {
        return domainListModel?.let {
            getPostUiModels(it)
        }
    }

    private fun getPostUiModels(userPostDomainModel: List<UserPostDomainModel>): List<PostUiModel> {
        return userPostDomainModel.map {
            when (it.status) {
                UserStatus.WITH_WARNING -> {
                    WarningUserPostUiModel(userId = it.userId, title = it.title, body = it.body, colorInt = Color.YELLOW)
                }
                UserStatus.BANNED -> {
                    BannedUserPostUiModel(userId = it.userId)
                }
                UserStatus.STANDARD -> {
                    StandardPostUiModel(userId = it.userId, title = it.title, body = it.body)
                }
            }
        }
    }
}