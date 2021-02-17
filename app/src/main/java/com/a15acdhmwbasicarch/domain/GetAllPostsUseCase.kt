package com.a15acdhmwbasicarch.domain

import com.a15acdhmwbasicarch.data.PostsInfoRepository
import com.a15acdhmwbasicarch.presentation.PostUiMapper
import com.a15acdhmwbasicarch.presentation.PostUiModel
import javax.inject.Inject

class GetAllPostsUseCase @Inject constructor(
    private val postRepository: PostsInfoRepository,
    private val postUiMapper: PostUiMapper
) {
    fun invoke(): List<PostUiModel>? = postUiMapper.map(postRepository.getInfo())
}