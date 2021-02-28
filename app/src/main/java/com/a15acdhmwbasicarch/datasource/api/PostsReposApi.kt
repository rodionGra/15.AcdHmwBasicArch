package com.a15acdhmwbasicarch.datasource.api

import com.a15acdhmwbasicarch.datasource.model.UserPostResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface PostsReposApi {
    @GET("/posts")
    fun getPostsList() : Single<List<UserPostResponse>>
}