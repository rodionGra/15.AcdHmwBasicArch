package com.a15acdhmwbasicarch.datasource.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.a15acdhmwbasicarch.datasource.model.UserPostData
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {
    @Query("SELECT * FROM UserPostData")
    fun getAllUsersFromDB(): Flow<List<UserPostData>>

    @Query("SELECT MAX(id) FROM UserPostData")
    suspend fun getMaxPostId(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(userPostData: UserPostData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListPosts(userPostData: List<UserPostData>)
}
