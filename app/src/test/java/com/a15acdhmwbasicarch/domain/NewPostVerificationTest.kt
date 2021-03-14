package com.a15acdhmwbasicarch.domain

import com.a15acdhmwbasicarch.data.AndroidResourceRepository
import com.a15acdhmwbasicarch.domain.model.NewPostModel
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

internal class NewPostVerificationTest {

    @TestFactory
    fun `check new post for qualification`(): List<DynamicTest> {
        val fakeForbiddenWord = "ForbiddenWord"
        val mockKAndroidResourceRepository = mockk<AndroidResourceRepository>() {
            every { getStringArray(any()) } returns arrayOf(fakeForbiddenWord)
        }

        val newPostVerification = NewPostVerification(mockKAndroidResourceRepository)

        return listOf(
            NewPostModel("normal title", "normal body") to VerificationStatus.Normal,
            NewPostModel("", "normal body") to VerificationStatus.Error(setOf(NewPostErrorType.TITLE_LENGTH_MIN_ERROR)),
            NewPostModel("normal title", "") to VerificationStatus.Error(setOf(NewPostErrorType.BODY_LENGTH_MIN_ERROR)),
            NewPostModel("$fakeForbiddenWord title", "normal body") to VerificationStatus.Error(setOf(NewPostErrorType.FORBIDDEN_WORDS_ERROR)),
        ).map { (post, newPostStatus) ->
            DynamicTest.dynamicTest("When post is $post status should by $newPostStatus") {
                assert(newPostVerification.verify(post) == newPostStatus)
            }
        }
    }
}