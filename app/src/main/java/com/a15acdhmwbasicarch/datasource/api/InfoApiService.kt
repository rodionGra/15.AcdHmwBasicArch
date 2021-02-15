package com.a15acdhmwbasicarch.data

import retrofit2.Call
import retrofit2.http.GET

interface InfoApiService {
    @GET("/posts")
    fun getPostsList() : Call<List<UserPostResponse>>
}