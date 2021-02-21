package com.a15acdhmwbasicarch.domain

import com.a15acdhmwbasicarch.data.PostsInfoRepository
import com.a15acdhmwbasicarch.datasource.model.AddedFrom
import com.a15acdhmwbasicarch.domain.model.UserPostDomainModel
import javax.inject.Inject

class GetAllPostsUseCase @Inject constructor(
    private val postRepository: PostsInfoRepository,
) {
    fun invoke(): List<UserPostDomainModel> {

        val postsGroupedByAddedFrom =
            postRepository.getPostsFromLocalStorage().groupBy { it.addedFrom }.withDefault { emptyList() }

        var listFromServer = postsGroupedByAddedFrom.getValue(AddedFrom.SERVER)
        listFromServer = listFromServer.sortedBy { it.id }

        var listFromUser = postsGroupedByAddedFrom.getValue(AddedFrom.USER)
        listFromUser = listFromUser.sortedByDescending { it.id }

        return listFromUser + listFromServer
    }
}