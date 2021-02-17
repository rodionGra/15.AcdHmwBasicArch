package com.a15acdhmwbasicarch.datasource.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.a15acdhmwbasicarch.datasource.model.UserPostData

@Dao
interface PostsDao {

    @Query("SELECT * FROM UserPostData")
    fun getAllUsersFromDB(): List<UserPostData>

    @Query("SELECT MAX(id) FROM UserPostData")
    fun getMaxPostId(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(userPostData: UserPostData)

    @Insert
    fun insertAll(vararg userPostData: UserPostData)
}