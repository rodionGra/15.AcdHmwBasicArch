package com.a15acdhmwbasicarch

import com.a15acdhmwbasicarch.data.UserPostResponse
import retrofit2.Call
import retrofit2.http.GET

interface InfoApiService {
    //TODO ASK ABOUT "/"
    @GET("/posts")
    fun getPostsList() : Call<List<UserPostResponse>>
}