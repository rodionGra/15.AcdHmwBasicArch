package com.a15acdhmwbasicarch.data

import com.a15acdhmwbasicarch.datasource.model.AddedFrom
import com.a15acdhmwbasicarch.datasource.model.UserPostData
import com.a15acdhmwbasicarch.datasource.model.UserPostResponse
import com.a15acdhmwbasicarch.tools.orZero
import javax.inject.Inject

class PostResponseToPostDbEntityMapper @Inject constructor() {

    fun map(usersPostsResponseList: List<UserPostResponse>): List<UserPostData> {
        return usersPostsResponseList.map {
            UserPostData(it.userId.orZero(), it.id.orZero(), it.title, it.body, AddedFrom.SERVER)
        }
    }

}
