package com.a15acdhmwbasicarch.di

import android.app.Application
import androidx.room.Room
import com.a15acdhmwbasicarch.datasource.db.PostDataBase
import com.a15acdhmwbasicarch.datasource.db.PostsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule(mApplication: Application) {

    @Singleton
    @Provides
    fun providesRoomDatabase(): PostDataBase {
        return demoDatabase
    }

    @Singleton
    @Provides
    fun providesProductDao(demoDatabase: PostDataBase): PostsDao {
        return demoDatabase.getPostsDao()
    }

    @Singleton
    private val demoDatabase: PostDataBase =
        Room.databaseBuilder(mApplication, PostDataBase::class.java, "posts-db2").build()

}