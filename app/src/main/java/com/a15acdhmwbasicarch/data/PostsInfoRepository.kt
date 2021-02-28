package com.a15acdhmwbasicarch.data

import com.a15acdhmwbasicarch.datasource.api.PostsReposApi
import com.a15acdhmwbasicarch.datasource.db.PostsDao
import com.a15acdhmwbasicarch.datasource.model.UserPostData
import com.a15acdhmwbasicarch.datasource.model.UserPostResponse
import com.a15acdhmwbasicarch.domain.model.UserPostDomainModel
import com.a15acdhmwbasicarch.tools.DataLoadingException
import io.reactivex.rxjava3.core.Completable
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

    fun updateLocalStorage(): Completable {
        localStorageLock.lock()

        val emitter = Completable.create { emitter ->
            val postFromApi = getPostsFromApi()
            if (postFromApi != null) {
                postsCacheDataSource.insertAll(*toDbMapper.map(postFromApi).toTypedArray())

                notifyAllObservers() //todo with rx
                emitter.onComplete()
            } else {
                emitter.onError(DataLoadingException("error get Posts From Api"))
            }
        }

        localStorageLock.unlock()
        return emitter
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
        Thread.sleep(5_000L)
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

