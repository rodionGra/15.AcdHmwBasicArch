package com.a15acdhmwbasicarch.data

import com.a15acdhmwbasicarch.datasource.api.PostsReposApi
import com.a15acdhmwbasicarch.datasource.db.PostsDao
import com.a15acdhmwbasicarch.datasource.model.UserPostData
import com.a15acdhmwbasicarch.datasource.model.UserPostResponse
import com.a15acdhmwbasicarch.domain.model.UserPostDomainModel
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PostsInfoRepository @Inject constructor(
    private val infoApiService: PostsReposApi,
    private val postsCacheDataSource: PostsDao,
    private val toDbMapper: PostResponseToPostDbEntityMapper,
    private val domainUserPostMapper: DomainUserPostMapper
) : Observable() {

    private val localStorageLock = ReentrantLock()

    fun getPostsFromLocalStorage(): List<UserPostDomainModel> {
        while (true) {
            if (!localStorageLock.isLocked) {
                return domainUserPostMapper.map(postsCacheDataSource.getAllUsersFromDB())
            }
        }
    }

    fun updateLocalStorage(): Boolean {
        localStorageLock.lock()
        getPostsFromApi()?.let { list ->
            toDbMapper.map(list).forEach {
                postsCacheDataSource.insertPost(it)
            }

            localStorageLock.unlock()
            notifyAllObservers()
            return true
        }
        localStorageLock.unlock()
        return false
    }

    fun addObserverFun(obj: Observer) {
        this.addObserver(obj)
    }

    fun putNewPost(post: UserPostData) {
        localStorageLock.lock()
        postsCacheDataSource.insertPost(post)
        localStorageLock.unlock()
        notifyAllObservers()
    }

    private fun getPostsFromApi(): List<UserPostResponse>? {
        return try {
            infoApiService.getPostsList().execute().body()
        } catch (e: Exception) {
            null
        }
    }

    private fun notifyAllObservers() {
        setChanged()
        notifyObservers()
    }

    fun getNewPostId() = postsCacheDataSource.getMaxPostId() + 1
}

