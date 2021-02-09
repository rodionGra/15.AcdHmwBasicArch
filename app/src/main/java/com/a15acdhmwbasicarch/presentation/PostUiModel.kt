package com.a15acdhmwbasicarch.presentation

sealed class PostUiModel {}

data class StandardPostUiModel(
        val userId: Int,
        val title: String,
        val body: String,
): PostUiModel()

data class WarningUserPostUiModel(
        val userId: Int,
        val title: String,
        val body: String,
        val colorInt: Int
): PostUiModel()

data class BannedUserPostUiModel(
        val userId: Int
): PostUiModel()

data class ErrorUiModel(
        val errorText: String
): PostUiModel()