package com.a15acdhmwbasicarch.domain

import com.a15acdhmwbasicarch.data.PostsInfoRepository
import com.a15acdhmwbasicarch.datasource.model.AddedFrom
import com.a15acdhmwbasicarch.domain.model.UserPostDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllPostsUseCase @Inject constructor(
    private val postRepository: PostsInfoRepository
) {

    fun invoke(): Flow<List<UserPostDomainModel>> =
        postRepository.getPostsFromLocalStorage().map(::sort)

    private fun sort(startList: List<UserPostDomainModel>): List<UserPostDomainModel> {
        val postsGroupedByAddedFrom = startList.groupBy { it.addedFrom }.withDefault { emptyList() }

        var listFromServer = postsGroupedByAddedFrom.getValue(AddedFrom.SERVER)
        listFromServer = listFromServer.sortedBy { it.id }

        var listFromUser = postsGroupedByAddedFrom.getValue(AddedFrom.USER)
        listFromUser = listFromUser.sortedByDescending { it.id }

        return listFromUser + listFromServer
    }
}