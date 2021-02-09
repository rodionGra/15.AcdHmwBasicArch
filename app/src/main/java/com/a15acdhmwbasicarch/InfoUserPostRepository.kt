package com.a15acdhmwbasicarch

import com.a15acdhmwbasicarch.data.UserStatusLocalDataSource
import com.a15acdhmwbasicarch.threading.AsyncOperation
import com.a15acdhmwbasicarch.threading.Multithreading
import com.a15acdhmwbasicarch.data.DomainUserPostMapper
import com.a15acdhmwbasicarch.data.UserPostResponse
import com.a15acdhmwbasicarch.domain.UserPostDomainModel

enum class UserPostError {
    MAIN_USER_POST_LIST_NOT_LOADED
}

class InfoUserPostRepository(
    private val infoApiService: InfoApiService,
    private val domainUserPostMapper: DomainUserPostMapper,
    private val postStatusLocalDataSource: UserStatusLocalDataSource
) {
    fun getInfo(): List<UserPostDomainModel>? {
        /**
         * Look at progress bar - main thread doesn't blocked
         */
        Thread.sleep(5000)
        val listOfPostUserInfo = infoApiService.getPostsList().execute().body()
        return listOfPostUserInfo?.let {
            domainUserPostMapper.map(it, postStatusLocalDataSource.getSetOfStatusUser())
        }
    }
}

