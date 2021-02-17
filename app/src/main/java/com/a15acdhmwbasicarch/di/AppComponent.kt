package com.a15acdhmwbasicarch.di

import android.content.Context
import com.a15acdhmwbasicarch.createNewPostFragment.CreateNewPostFragment
import com.a15acdhmwbasicarch.showPostsFragment.ShowAllPostsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [], modules = [AppModule::class, RoomModule::class])
interface AppComponent {
    val context : Context

    fun inject(fragment: ShowAllPostsFragment)
    fun inject(fragment: CreateNewPostFragment)
}