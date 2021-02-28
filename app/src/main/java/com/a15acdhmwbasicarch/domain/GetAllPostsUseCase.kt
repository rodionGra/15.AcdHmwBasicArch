package com.a15acdhmwbasicarch.domain

import com.a15acdhmwbasicarch.data.PostsInfoRepository
import com.a15acdhmwbasicarch.datasource.model.AddedFrom
import com.a15acdhmwbasicarch.domain.model.UserPostDomainModel
import io.reactivex.Flowable
import javax.inject.Inject

class GetAllPostsUseCase @Inject constructor(
    private val postRepository: PostsInfoRepository,
    private val sortClass: SortByAddedFromAndId
) {
    //add sort
    fun invoke(): Flowable<List<UserPostDomainModel>> =
        postRepository.getPostsFromLocalStorage().map(sortClass::sort)
}

class SortByAddedFromAndId @Inject constructor() {
    fun sort(startList: List<UserPostDomainModel>): List<UserPostDomainModel> {
        val postsGroupedByAddedFrom = startList.groupBy { it.addedFrom }.withDefault { emptyList() }

        var listFromServer = postsGroupedByAddedFrom.getValue(AddedFrom.SERVER)
        listFromServer = listFromServer.sortedBy { it.id }

        var listFromUser = postsGroupedByAddedFrom.getValue(AddedFrom.USER)
        listFromUser = listFromUser.sortedByDescending { it.id }

        return listFromUser + listFromServer
    }
}