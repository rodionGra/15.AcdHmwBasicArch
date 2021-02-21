package com.a15acdhmwbasicarch.domain

import com.a15acdhmwbasicarch.data.PostsInfoRepository
import com.a15acdhmwbasicarch.datasource.model.AddedFrom
import com.a15acdhmwbasicarch.datasource.model.UserPostData
import com.a15acdhmwbasicarch.domain.model.NewPostModel
import javax.inject.Inject

class SavePostUseCase @Inject constructor(
    private val repository: PostsInfoRepository
) {
    fun savePost(postForSaving: NewPostModel) {
        repository.putNewPost(mapNewPostToDataPostModel(postForSaving))
    }

    private fun mapNewPostToDataPostModel(postForSaving: NewPostModel) =
        UserPostData(
            555,
            repository.getNewPostId(),
            postForSaving.title,
            postForSaving.body,
            AddedFrom.USER
        )

}