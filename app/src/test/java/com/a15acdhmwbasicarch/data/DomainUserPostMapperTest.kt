package com.a15acdhmwbasicarch.data

import com.a15acdhmwbasicarch.datasource.UserStatusLocalDataSource
import com.a15acdhmwbasicarch.datasource.model.AddedFrom
import com.a15acdhmwbasicarch.datasource.model.StatusUser
import com.a15acdhmwbasicarch.datasource.model.UserPostData
import com.a15acdhmwbasicarch.domain.PostStatus
import com.a15acdhmwbasicarch.domain.model.UserPostDomainModel
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

internal class DomainUserPostMapperTest {

    private val postId = 1
    private val userId = 1
    private val title = "title"
    private val body = "body"
    private val addedFrom = AddedFrom.SERVER

    @Test
    fun `map data post to domain post with standard status`() {

        val mockKUserStatusLocalDataSource = mockk<UserStatusLocalDataSource> {
            every { getSetOfStatusUser() } returns setOf(StatusUser(userId, PostStatus.STANDARD))
        }

        val domainUserPostMapper = DomainUserPostMapper(mockKUserStatusLocalDataSource)

        val dataPostList = listOf(UserPostData(userId, postId, title, body, addedFrom))

        val actualPostList = domainUserPostMapper.map(dataPostList)

        val expectedPostList = listOf(
            UserPostDomainModel(userId, postId, title, body, PostStatus.STANDARD, addedFrom)
        )

        actualPostList shouldBe expectedPostList
    }

    @Test
    fun `map data post to domain post with banned status`() {

        val mockKUserStatusLocalDataSource = mockk<UserStatusLocalDataSource> {
            every { getSetOfStatusUser() } returns setOf(StatusUser(userId, PostStatus.BANNED))
        }

        val domainUserPostMapper = DomainUserPostMapper(mockKUserStatusLocalDataSource)

        val dataPostList = listOf(UserPostData(userId, postId, title, body, addedFrom))

        val actualPostList = domainUserPostMapper.map(dataPostList)

        val expectedPostList = listOf(
            UserPostDomainModel(userId, postId, title, body, PostStatus.BANNED, addedFrom)
        )

        actualPostList shouldBe expectedPostList
    }

    @Test
    fun `map data post to domain post with warning status`() {

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

        actualPostList shouldBe expectedPostList
    }
}