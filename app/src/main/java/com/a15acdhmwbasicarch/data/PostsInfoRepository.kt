package com.a15acdhmwbasicarch.data

import com.a15acdhmwbasicarch.datasource.api.PostsReposApi
import com.a15acdhmwbasicarch.datasource.db.PostsDao
import com.a15acdhmwbasicarch.datasource.model.UserPostData
import com.a15acdhmwbasicarch.domain.model.UserPostDomainModel
import javax.inject.Inject

class PostsInfoRepository @Inject constructor(
    private val infoApiService: PostsReposApi,
    private val domainUserPostMapper: DomainUserPostMapper,
    private val postsCacheDataSource: PostsDao
) {
    fun getInfo(): List<UserPostDomainModel>? {
        val resultList: MutableList<UserPostDomainModel> = mutableListOf()

        getPostsFromApi().let { list ->
            list?.forEach {
                postsCacheDataSource.insertPost(it)
            }
        }

        resultList.addAll(getPostsFromCache() ?: emptyList())

        return resultList
    }

    fun putNewPost(post : UserPostData){
        postsCacheDataSource.insertPost(post)
    }

    fun getNewPostId() = postsCacheDataSource.getMaxPostId() + 1

    private fun getPostsFromApi(): List<UserPostData>? {
        return infoApiService.getPostsList().execute().body()
    }

    private fun getPostsFromCache(): List<UserPostDomainModel>? {
        return domainUserPostMapper.map(postsCacheDataSource.getAllUsersFromDB())
    }
}

