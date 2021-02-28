package com.a15acdhmwbasicarch.data

import com.a15acdhmwbasicarch.datasource.api.PostsReposApi
import com.a15acdhmwbasicarch.datasource.db.PostsDao
import com.a15acdhmwbasicarch.datasource.model.UserPostData
import com.a15acdhmwbasicarch.datasource.model.UserPostResponse
import com.a15acdhmwbasicarch.domain.model.UserPostDomainModel
import com.a15acdhmwbasicarch.tools.DataLoadingException
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
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
            getPostsFromApi()
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

    fun putNewPost(post: UserPostData) {
        localStorageLock.lock()
        postsCacheDataSource.insertPost(post)
        localStorageLock.unlock()
    }

    private fun getPostsFromApi(): Single<List<UserPostResponse>> {
        return infoApiService.getPostsList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getNewPostId() = postsCacheDataSource.getMaxPostId() + 1
}

