package com.a15acdhmwbasicarch

import com.a15acdhmwbasicarch.data.UserStatusLocalDataSource
import com.a15acdhmwbasicarch.threading.AsyncOperation
import com.a15acdhmwbasicarch.threading.Multithreading
import com.a15acdhmwbasicarch.data.DomainUserPostMapper
import com.a15acdhmwbasicarch.data.UserPostResponse
import com.a15acdhmwbasicarch.domain.UserPostDomainModel

enum class UserPostError{
    MAIN_USER_POST_LIST_NOT_LOADED
}

class InfoUserPostRepository(
        private val multithreading: Multithreading,
        private val infoApiService: InfoApiService,
        private val domainUserPostMapper: DomainUserPostMapper,
        private val postStatusLocalDataSource: UserStatusLocalDataSource
) {
    fun getInfo(): AsyncOperation<Result<List<UserPostDomainModel>, UserPostError>>{

        val asyncOperation : AsyncOperation<Result<List<UserPostResponse>, UserPostError>> = multithreading.async<Result<List<UserPostResponse>, UserPostError>>{
            val listOfPostUserInfo = infoApiService.getPostsList().execute().body()
                ?: return@async Result.error(UserPostError.MAIN_USER_POST_LIST_NOT_LOADED)

            return@async Result.success(listOfPostUserInfo)
        }

        return asyncOperation.map {
            domainUserPostMapper.map(it, postStatusLocalDataSource.getSetOfStatusUser())
        }
    }
}

