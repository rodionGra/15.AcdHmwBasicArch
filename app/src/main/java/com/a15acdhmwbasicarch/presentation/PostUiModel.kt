package com.a15acdhmwbasicarch.presentation

import androidx.annotation.ColorInt

sealed class PostUiModel {
    abstract val postId: Int
}

data class StandardPostUiModel(
    override val postId: Int,
    val userId: String,
    val title: String,
    val body: String,
    val hasWarning: Boolean,
    @ColorInt val backgroundColor: Int
) : PostUiModel()

data class BannedUserPostUiModel(
    override val postId: Int,
    val userId: Int
) : PostUiModel()
