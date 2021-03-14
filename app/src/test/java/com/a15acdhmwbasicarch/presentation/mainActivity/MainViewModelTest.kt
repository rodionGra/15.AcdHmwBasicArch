package com.a15acdhmwbasicarch.presentation.mainActivity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.a15acdhmwbasicarch.MainCoroutineRule
import com.a15acdhmwbasicarch.data.PostsInfoRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class MainViewModelTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = MainCoroutineRule()

    @Test
    fun `update local storage`(){
        val mockKRepository = mockk<PostsInfoRepository>{
            coEvery { updateLocalStorage() } returns Unit
        }

        val mainViewModel = MainViewModel(mockKRepository)

        //mainViewModel.updateRepo() shouldBe Unit

        //mainViewModel.errorLiveData.value shouldBe UpdatingState.COMPLETED
    }
}