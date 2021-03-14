package com.a15acdhmwbasicarch.domain

import com.a15acdhmwbasicarch.data.PostsInfoRepository
import com.a15acdhmwbasicarch.datasource.model.AddedFrom
import com.a15acdhmwbasicarch.domain.model.UserPostDomainModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class GetAllSortedPostsUseCaseTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @Test
    fun `sort flow by adding resource and post id`() {
        val currentFlow = flowOf(
            listOf(
                UserPostDomainModel(1, 2, "title", "body", PostStatus.STANDARD, AddedFrom.SERVER),
                UserPostDomainModel(1, 1, "title", "body", PostStatus.STANDARD, AddedFrom.SERVER),
                UserPostDomainModel(1, 3, "title", "body", PostStatus.STANDARD, AddedFrom.USER),
                UserPostDomainModel(1, 4, "title", "body", PostStatus.STANDARD, AddedFrom.USER)
            )
        )

        val mockKRepository = mockk<PostsInfoRepository>() {
            every { getPostsFromLocalStorage() } returns currentFlow
        }

        val expectedFlow = listOf(
            listOf(
                UserPostDomainModel(1, 4, "title", "body", PostStatus.STANDARD, AddedFrom.USER),
                UserPostDomainModel(1, 3, "title", "body", PostStatus.STANDARD, AddedFrom.USER),
                UserPostDomainModel(1, 1, "title", "body", PostStatus.STANDARD, AddedFrom.SERVER),
                UserPostDomainModel(1, 2, "title", "body", PostStatus.STANDARD, AddedFrom.SERVER)
            )
        )

        testDispatcher.runBlockingTest {
            assertEquals(
                expectedFlow,
                GetAllSortedPostsUseCase(mockKRepository, testDispatcher)().toList()
            )
        }
    }

}