package com.a15acdhmwbasicarch.data

import com.a15acdhmwbasicarch.datasource.UserStatusLocalDataSource
import com.a15acdhmwbasicarch.datasource.model.AddedFrom
import com.a15acdhmwbasicarch.datasource.model.StatusUser
import com.a15acdhmwbasicarch.datasource.model.UserPostData
import com.a15acdhmwbasicarch.domain.PostStatus
import com.a15acdhmwbasicarch.domain.model.UserPostDomainModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class DomainUserPostMapperTest {

    @Test
    fun `map data post to domain post with standard status`() {

        val postId = 1
        val userId = 1
        val title = "title"
        val body = "body"
        val addedFrom = AddedFrom.SERVER

        val mockKUserStatusLocalDataSource = mockk<UserStatusLocalDataSource> {
            every { getSetOfStatusUser() } returns setOf(StatusUser(userId, PostStatus.STANDARD))
        }

        val domainUserPostMapper = DomainUserPostMapper(mockKUserStatusLocalDataSource)

        val dataPostList = listOf(UserPostData(userId, postId, title, body, addedFrom))

        val actualPostList = domainUserPostMapper.map(dataPostList)

        val expectedPostList = listOf(
            UserPostDomainModel(userId, postId, title, body, PostStatus.STANDARD, addedFrom)
        )

        assertEquals(expectedPostList, actualPostList)
    }

    @Test
    fun `map data post to domain post with banned status`() {
        val postId = 1
        val userId = 1
        val title = "title"
        val body = "body"
        val addedFrom = AddedFrom.SERVER

        val mockKUserStatusLocalDataSource = mockk<UserStatusLocalDataSource> {
            every { getSetOfStatusUser() } returns setOf(StatusUser(userId, PostStatus.BANNED))
        }

        val domainUserPostMapper = DomainUserPostMapper(mockKUserStatusLocalDataSource)

        val dataPostList = listOf(UserPostData(userId, postId, title, body, addedFrom))

        val actualPostList = domainUserPostMapper.map(dataPostList)

        val expectedPostList = listOf(
            UserPostDomainModel(userId, postId, title, body, PostStatus.BANNED, addedFrom)
        )

        assertEquals(expectedPostList, actualPostList)
    }

    @Test
    fun `map data post to domain post with warning status`() {
        val postId = 1
        val userId = 1
        val title = "title"
        val body = "body"
        val addedFrom = AddedFrom.SERVER

        val mockKUserStatusLocalDataSource = mockk<UserStatusLocalDataSource> {
            every { getSetOfStatusUser() } returns setOf(
                StatusUser(userId, PostStatus.WITH_WARNING)
            )
        }

        val domainUserPostMapper = DomainUserPostMapper(mockKUserStatusLocalDataSource)

        val dataPostList = listOf(UserPostData(userId, postId, title, body, addedFrom))

        val actualPostList = domainUserPostMapper.map(dataPostList)

        val expectedPostList = listOf(
            UserPostDomainModel(userId, postId, title, body, PostStatus.WITH_WARNING, addedFrom)
        )

        assertEquals(expectedPostList, actualPostList)
    }
}