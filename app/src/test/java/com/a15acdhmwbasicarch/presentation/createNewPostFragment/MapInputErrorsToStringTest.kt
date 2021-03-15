package com.a15acdhmwbasicarch.presentation.createNewPostFragment

import com.a15acdhmwbasicarch.R
import com.a15acdhmwbasicarch.data.AndroidResourceRepository
import com.a15acdhmwbasicarch.domain.NewPostErrorType
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class MapInputErrorsToStringTest {

    @Test
    fun `map set of error to string`() {
        val setOfError = setOf(
            NewPostErrorType.BODY_LENGTH_MIN_ERROR,
            NewPostErrorType.FORBIDDEN_WORDS_ERROR
        )

        val mockKAndroidResourceRepository = mockk<AndroidResourceRepository> {
            every { getString(any()) } returns "Some error text"
        }

        val mapInputErrorsToString = MapInputErrorsToString(
            mockKAndroidResourceRepository,
            TestCoroutineDispatcher()
        )

        val expectedErrorString =
            "Some error text\nSome error text\n"

        runBlockingTest {
            assertEquals(
                expectedErrorString,
                mapInputErrorsToString.invoke(setOfError)
            )
        }
    }
}