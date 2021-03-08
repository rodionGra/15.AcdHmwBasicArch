package com.a15acdhmwbasicarch.domain

import com.a15acdhmwbasicarch.data.PostsInfoRepository
import com.a15acdhmwbasicarch.domain.model.NewPostModel
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class AddNewPostValidationUseCaseTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @Test
    fun `if post verification passed then post is created`() {
        val mockKPostsInfoRepository = mockk<PostsInfoRepository>(relaxUnitFun = true)
        val mockKNewPostValidation = mockk<NewPostVerification>() {
            every { verify(any()) } returns VerificationStatus.Normal
        }

        val addNewPostValidationUseCase =
            AddNewPostValidationUseCase(mockKPostsInfoRepository, mockKNewPostValidation)

        val post = NewPostModel("normal title", "normal body")

        testDispatcher.runBlockingTest {
            addNewPostValidationUseCase(post)
        }

        verify() {
            testDispatcher.runBlockingTest {
                mockKPostsInfoRepository.saveNewPostFromUser(post)
            }
        }

        confirmVerified(mockKPostsInfoRepository)
    }

    @Test
    fun `if post verification did not pass then post isn't created`() {
        val mockKPostsInfoRepository = mockk<PostsInfoRepository>(relaxUnitFun = true)
        val mockKNewPostValidation = mockk<NewPostVerification>() {
            every { verify(any()) } returns VerificationStatus.Error(setOf(NewPostErrorType.BODY_LENGTH_MAX_ERROR))
        }

        val addNewPostValidationUseCase =
            AddNewPostValidationUseCase(mockKPostsInfoRepository, mockKNewPostValidation)

        val post = NewPostModel("normal title", "normal body")

        testDispatcher.runBlockingTest {
            addNewPostValidationUseCase(post)
        }

        verify(exactly = 0) {
            testDispatcher.runBlockingTest {
                mockKPostsInfoRepository.saveNewPostFromUser(post)
            }
        }
    }
}