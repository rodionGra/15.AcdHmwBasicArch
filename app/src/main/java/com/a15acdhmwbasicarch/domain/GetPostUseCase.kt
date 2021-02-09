package com.a15acdhmwbasicarch.domain

import com.a15acdhmwbasicarch.InfoUserPostRepository
import com.a15acdhmwbasicarch.UserPostError
import com.a15acdhmwbasicarch.presentation.PostUiMapper
import com.a15acdhmwbasicarch.presentation.PostUiModel
import com.a15acdhmwbasicarch.threading.AsyncOperation
import com.a15acdhmwbasicarch.Result

class GetPostUseCase(
    private val postRepository: InfoUserPostRepository,
    private val postUiMapper: PostUiMapper
) {
    fun funInvoke(): List<PostUiModel>? = postUiMapper.map(postRepository.getInfo())
}