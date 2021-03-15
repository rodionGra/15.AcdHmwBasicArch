package com.a15acdhmwbasicarch.di

import android.content.Context
import com.a15acdhmwbasicarch.presentation.createNewPostFragment.CreateNewPostFragment
import com.a15acdhmwbasicarch.presentation.mainActivity.MainActivity
import com.a15acdhmwbasicarch.presentation.showPostsFragment.ShowAllPostsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [],
    modules = [AppModule::class, RoomModule::class, DispatcherModule::class]
)
interface AppComponent {
    val context: Context

    fun inject(fragment: ShowAllPostsFragment)
    fun inject(fragment: CreateNewPostFragment)
    fun inject(activity: MainActivity)
}
