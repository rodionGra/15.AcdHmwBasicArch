package com.a15acdhmwbasicarch.data

import com.a15acdhmwbasicarch.datasource.PostsCacheDataSource
import com.a15acdhmwbasicarch.datasource.api.InfoApiService
import com.a15acdhmwbasicarch.datasource.model.UserPostResponse
import com.a15acdhmwbasicarch.domain.UserPostDomainModel

class PostsInfoRepository(
    private val infoApiService: InfoApiService,
    private val domainUserPostMapper: DomainUserPostMapper,
    private val postsCacheDataSource: PostsCacheDataSource
) {
    fun getInfo(): List<UserPostDomainModel>? {
        //todo clean up list from back
        val listOfPostUserInfo : List<UserPostResponse>? = infoApiService.getPostsList().execute().body()

        //todo clean up list from cache
        val result: MutableList<UserPostResponse> = mutableListOf()
        result.addAll(postsCacheDataSource.getListPostsUserCacheData())
        listOfPostUserInfo?.let { result.addAll(it) }

        return result.toList().let(domainUserPostMapper::map)
    }
}

