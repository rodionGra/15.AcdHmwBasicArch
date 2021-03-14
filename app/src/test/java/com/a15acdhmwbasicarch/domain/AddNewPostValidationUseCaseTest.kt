package com.a15acdhmwbasicarch.domain

import com.a15acdhmwbasicarch.data.PostsInfoRepository
import com.a15acdhmwbasicarch.domain.model.NewPostModel
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

internal class AddNewPostValidationUseCaseTest {

    @Test
    fun `if post verification passed then post is created`() {
        val mockKPostsInfoRepository = mockk<PostsInfoRepository>(relaxUnitFun = true)
        val mockKNewPostValidation = mockk<NewPostVerification>() {
            every { verify(any()) } returns VerificationStatus.Normal
        }

        val addNewPostValidationUseCase =
            AddNewPostValidationUseCase(mockKPostsInfoRepository, mockKNewPostValidation)

        val post = NewPostModel("normal title", "normal body")

        runBlocking {
            addNewPostValidationUseCase(post)
        }

        coVerify {
            mockKPostsInfoRepository.saveNewPostFromUser(post)
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

        runBlocking {
            addNewPostValidationUseCase(post)
        }

        coVerify(exactly = 0) {
            mockKPostsInfoRepository.saveNewPostFromUser(post)
        }
    }
}