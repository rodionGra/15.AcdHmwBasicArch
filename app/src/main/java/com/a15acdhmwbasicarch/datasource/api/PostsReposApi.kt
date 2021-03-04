package com.a15acdhmwbasicarch.datasource.api

import com.a15acdhmwbasicarch.datasource.model.UserPostResponse
import retrofit2.Response
import retrofit2.http.GET

interface PostsReposApi {
    @GET("/posts")
    suspend fun getPostsList(): Response<List<UserPostResponse>>
}