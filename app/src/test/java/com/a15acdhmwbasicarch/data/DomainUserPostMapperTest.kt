package com.a15acdhmwbasicarch.data

import com.a15acdhmwbasicarch.datasource.UserStatusLocalDataSource
import com.a15acdhmwbasicarch.datasource.model.AddedFrom
import com.a15acdhmwbasicarch.datasource.model.StatusUser
import com.a15acdhmwbasicarch.datasource.model.UserPostData
import com.a15acdhmwbasicarch.domain.PostStatus
import com.a15acdhmwbasicarch.domain.model.UserPostDomainModel
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class DomainUserPostMapperTest {

    @TestFactory
    fun `map Post to PostModel`(): List<DynamicTest> {
        return listOf(
            PostStatus.STANDARD,
            PostStatus.WITH_WARNING,
            PostStatus.BANNED
        ).map { status ->
            DynamicTest.dynamicTest("if user has $status that domain model should has the same status") {
                val postId = 1
                val userId = 1
                val title = "title"
                val body = "body"
                val addedFrom = AddedFrom.SERVER

                val mockKUserStatusLocalDataSource = mockk<UserStatusLocalDataSource> {
                    every { getSetOfStatusUser() } returns setOf(
                        StatusUser(userId, status)
                    )
                }

                val domainUserPostMapper = DomainUserPostMapper(mockKUserStatusLocalDataSource)

                val dataPostList = listOf(UserPostData(userId, postId, title, body, addedFrom))

                //test
                val actualPostList = domainUserPostMapper.map(dataPostList)

                val expectedPostList = listOf(
                    UserPostDomainModel(
                        userId,
                        postId,
                        title,
                        body,
                        status,
                        addedFrom
                    )
                )

                assertEquals(expectedPostList, actualPostList)
            }
        }
    }
}