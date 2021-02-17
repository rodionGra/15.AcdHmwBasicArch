package com.a15acdhmwbasicarch.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.a15acdhmwbasicarch.datasource.model.UserPostData

@Database(entities = [UserPostData::class], version = PostDataBase.VERSION)
abstract class PostDataBase : RoomDatabase() {
    companion object {
        const val VERSION = 1
    }

    abstract fun getPostsDao(): PostsDao
}