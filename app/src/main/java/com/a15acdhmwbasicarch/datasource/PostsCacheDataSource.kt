package com.a15acdhmwbasicarch.datasource

import com.a15acdhmwbasicarch.datasource.model.UserPostResponse

object PostsCacheDataSource {
    private val listPostsUserCacheData =
        linkedSetOf(
            UserPostResponse(11, 101, "textTitle", "textBody"),
            UserPostResponse(45, 101, "textTitle", "textBody"),
            UserPostResponse(65456, 101, "textTitle", "textBody"),
            UserPostResponse(1, 101, "textTitle", "textBody"),
            UserPostResponse(11, 101, "textTitle", "textBody"),
            UserPostResponse(87, 101, "textTitle", "textBody"),
            UserPostResponse(9874, 101, "textTitle", "textBody")
        )


    fun addPostsOnStart(userPostResponse: UserPostResponse) {
        listPostsUserCacheData.add(userPostResponse)
    }

    fun getListPostsUserCacheData(): List<UserPostResponse> = listPostsUserCacheData.toList()

    fun getBiggestPostId() : Int {
        return listPostsUserCacheData.maxByOrNull { it.id }?.id ?: 1
    }

    fun getBiggestUserId() : Int {
        return listPostsUserCacheData.maxByOrNull { it.userId }?.userId ?: 1
    }
}