package com.a15acdhmwbasicarch.presentation.createNewPostFragment

import com.a15acdhmwbasicarch.domain.AddNewPostValidationUseCase
import com.a15acdhmwbasicarch.domain.NewPostErrorType
import com.a15acdhmwbasicarch.domain.VerificationStatus
import com.a15acdhmwbasicarch.tools.CoroutinesTestExtension
import com.a15acdhmwbasicarch.tools.InstantExecutorExtension
import com.a15acdhmwbasicarch.tools.getOrAwaitValueTest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(
    InstantExecutorExtension::class,
    CoroutinesTestExtension::class
)
@ExperimentalCoroutinesApi
internal class CreateNewPostViewModelTest {

    @Test
    fun `success verification new post`() {
        val mockKValidationUseCase = mockk<AddNewPostValidationUseCase> {
            coEvery { this@mockk.invoke(any()) } returns VerificationStatus.Normal
        }
        val mockKMapInputErrorsToString = mockk<MapInputErrorsToString>()

        val createNewPostViewModel =
            CreateNewPostViewModel(mockKValidationUseCase, mockKMapInputErrorsToString)

        createNewPostViewModel.sendDataToCache("", "")

        val liveDataValue = createNewPostViewModel.stringErrorLiveData.getOrAwaitValueTest()

        assertEquals(VerificationStatus.Normal, liveDataValue)
    }

    @Test
    fun `fail verification new post`() {
        val mockKValidationUseCase = mockk<AddNewPostValidationUseCase> {
            coEvery { this@mockk.invoke(any()) } returns VerificationStatus.Error(
                setOf(
                    NewPostErrorType.BODY_LENGTH_MIN_ERROR
                )
            )
        }
        val mockKMapInputErrorsToString = mockk<MapInputErrorsToString> {
            coEvery { this@mockk.invoke(any()) } returns "Body must contain at less 5 characters"
        }

        val createNewPostViewModel =
            CreateNewPostViewModel(mockKValidationUseCase, mockKMapInputErrorsToString)

        createNewPostViewModel.sendDataToCache("", "")

        val liveDataValue = createNewPostViewModel.stringErrorLiveData.getOrAwaitValueTest()

        assertEquals(
            VerificationStatus.Error("Body must contain at less 5 characters"),
            liveDataValue
        )
    }
}
