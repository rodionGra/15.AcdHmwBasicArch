package com.a15acdhmwbasicarch.data

import android.annotation.SuppressLint
import com.a15acdhmwbasicarch.datasource.api.PostsReposApi
import com.a15acdhmwbasicarch.datasource.db.PostsDao
import com.a15acdhmwbasicarch.datasource.model.UserPostData
import com.a15acdhmwbasicarch.datasource.model.UserPostResponse
import com.a15acdhmwbasicarch.domain.model.NewPostModel
import com.a15acdhmwbasicarch.domain.model.UserPostDomainModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsInfoRepository @Inject constructor(
    private val infoApiService: PostsReposApi,
    private val postsCacheDataSource: PostsDao,
    private val toDbMapper: PostResponseToPostDbEntityMapper,
    private val domainUserPostMapper: DomainUserPostMapper,
    private val mapNewPostToDataPostModel: NewPostToDataPostMapper
) {

    private val localStorageLock = ReentrantLock()

    fun getPostsFromLocalStorage(): Flowable<List<UserPostDomainModel>> {
        while (true) {
            if (!localStorageLock.isLocked) {
                return postsCacheDataSource.getAllUsersFromDB().map(domainUserPostMapper::map)
            }
        }
    }

    fun updateLocalStorage(): Completable {
        localStorageLock.lock()

        val emitter = Completable.create { emitter ->
            infoApiService.getPostsList()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                    { listUserPostResponse ->
                        toDbMapper.map(listUserPostResponse).forEach {
                            postsCacheDataSource.insertPost(it)
                        }
                        emitter.onComplete()
                    },
                    {
                        emitter.onError(it)
                    }
                )
        }

        localStorageLock.unlock()
        return emitter
    }


    /*//todo
    fun getNewPostId(): Single<Int> {
        return postsCacheDataSource.getMaxPostId().map { it + 1 }
    }*/

    private fun getNewPostId(): Int {
        return postsCacheDataSource.getMaxPostId() + 1
    }

    fun saveNewPostFromUser(postForSaving: NewPostModel) {
        postsCacheDataSource.insertPost(mapNewPostToDataPostModel.map(postForSaving, getNewPostId()))
    }
}

