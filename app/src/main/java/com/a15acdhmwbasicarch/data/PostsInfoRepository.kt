package com.a15acdhmwbasicarch.data

import com.a15acdhmwbasicarch.datasource.api.PostsReposApi
import com.a15acdhmwbasicarch.datasource.db.PostsDao
import com.a15acdhmwbasicarch.datasource.model.UserPostData
import com.a15acdhmwbasicarch.di.IoDispatcher
import com.a15acdhmwbasicarch.domain.model.NewPostModel
import com.a15acdhmwbasicarch.domain.model.UserPostDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsInfoRepository @Inject constructor(
    private val infoApiService: PostsReposApi,
    private val postsCacheDataSource: PostsDao,
    private val toDbMapper: PostResponseToPostDbEntityMapper,
    private val domainUserPostMapper: DomainUserPostMapper,
    private val mapNewPostToDataPostModel: NewPostToDataPostMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    fun getPostsFromLocalStorage(): Flow<List<UserPostDomainModel>> {
        return postsCacheDataSource.getAllUsersFromDB().map(domainUserPostMapper::map)
    }

    suspend fun updateLocalStorage() = withContext(ioDispatcher) {
        val response = infoApiService.getPostsList()
        if (response.isSuccessful) {
            response.body()?.let {
                val listToBd: List<UserPostData> = toDbMapper.map(it)
                postsCacheDataSource.insertListPosts(listToBd)
            }
        } else {
            throw Exception(response.errorBody().toString())
        }
    }

    private suspend fun getNewPostId(): Int {
        return withContext(ioDispatcher) {
            postsCacheDataSource.getMaxPostId() + 1
        }
    }

    suspend fun saveNewPostFromUser(postForSaving: NewPostModel) = withContext(ioDispatcher) {
        postsCacheDataSource.insertPost(
            mapNewPostToDataPostModel.map(
                postForSaving,
                getNewPostId()
            )
        )
    }
}