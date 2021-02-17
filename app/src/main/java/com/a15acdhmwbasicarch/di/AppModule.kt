package com.a15acdhmwbasicarch.di

import android.content.Context
import com.a15acdhmwbasicarch.data.PostsInfoRepository
import com.a15acdhmwbasicarch.datasource.api.PostsReposApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {

    @Singleton
    @Provides //todo scope is not necessary for parameters stored within the module
    fun context(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder().setLenient().create()
        )
    }

    @Provides
    @Singleton
    fun provideGitHubReposApi(retrofit: Retrofit): PostsReposApi {
        return retrofit.create(PostsReposApi::class.java)
    }

    //todo singleton repository if necessary
    /*@Provides
    @Singleton
    fun providePostRepository(): PostsInfoRepository{
        return PostsInfoRepository()
    }*/

}