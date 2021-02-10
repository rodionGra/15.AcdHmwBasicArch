package com.a15acdhmwbasicarch.presentation

import androidx.annotation.ColorInt

sealed class PostUiModel() {abstract val postId: Int}

data class StandardPostUiModel(
        override val postId: Int,
        val userId: Int,
        val title: String,
        val body: String
): PostUiModel()

data class WarningUserPostUiModel(
        override val postId : Int,
        val userId: Int,
        val title: String,
        val body: String,
        val colors: PostColors,
        val warningText: String
): PostUiModel()

data class BannedUserPostUiModel(
        override val postId: Int,
        val banText: String
): PostUiModel()

/*data class ErrorUiModel(
        val errorText: String
): PostUiModel()*/

data class PostColors(
        @ColorInt val backgroundColor: Int
)