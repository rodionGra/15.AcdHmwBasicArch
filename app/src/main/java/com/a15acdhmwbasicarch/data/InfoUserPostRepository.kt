package com.a15acdhmwbasicarch

import com.a15acdhmwbasicarch.data.DomainUserPostMapper
import com.a15acdhmwbasicarch.domain.UserPostDomainModel

class InfoUserPostRepository(
    private val infoApiService: InfoApiService,
    private val domainUserPostMapper: DomainUserPostMapper
) {
    fun getInfo(): List<UserPostDomainModel>? {
        val listOfPostUserInfo = infoApiService.getPostsList().execute().body()

        return listOfPostUserInfo?.let(domainUserPostMapper::map)
    }
}

