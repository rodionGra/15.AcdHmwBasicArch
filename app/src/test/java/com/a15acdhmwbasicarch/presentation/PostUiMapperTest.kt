package com.a15acdhmwbasicarch.presentation

import com.a15acdhmwbasicarch.R
import com.a15acdhmwbasicarch.data.AndroidResourceRepository
import com.a15acdhmwbasicarch.datasource.model.AddedFrom
import com.a15acdhmwbasicarch.domain.PostStatus
import com.a15acdhmwbasicarch.domain.model.UserPostDomainModel
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class PostUiMapperTest {

    @Test
    fun `test map domain model to ui model`() {
        val postId = 1
        val userId = 5
        val title = "title"
        val body = "body"

        val normalColor = 1
        val warningColor = 5
        val mockKAndroidResourceRepository = mockk<AndroidResourceRepository>() {
            every { getColor(R.color.white) } returns normalColor
            every { getColor(R.color.red) } returns warningColor
        }

        val postUiMapper = PostUiMapper(mockKAndroidResourceRepository)

        val listStandardPostUiModel = listOf(
            UserPostDomainModel(userId, postId, title, body, PostStatus.STANDARD, AddedFrom.USER),
            UserPostDomainModel(userId, postId, title, body, PostStatus.WITH_WARNING, AddedFrom.USER),
            UserPostDomainModel(userId, postId, title, body, PostStatus.BANNED, AddedFrom.USER),
        )

        val expectedPostUiList = listOf(
            StandardPostUiModel(postId, userId.toString(), title, body, false, normalColor),
            StandardPostUiModel(postId, userId.toString(), title, body, true, warningColor),
            BannedUserPostUiModel(postId, userId)
        )

        postUiMapper.map(listStandardPostUiModel) shouldBe expectedPostUiList
    }
}