package com.a15acdhmwbasicarch.domain

import com.a15acdhmwbasicarch.data.InfoUserPostRepository
import com.a15acdhmwbasicarch.presentation.PostUiMapper
import com.a15acdhmwbasicarch.presentation.PostUiModel

class GetPostUseCase(
    private val postRepository: InfoUserPostRepository,
    private val postUiMapper: PostUiMapper
) {
    fun invoke(): List<PostUiModel>? = postUiMapper.map(postRepository.getInfo())
}