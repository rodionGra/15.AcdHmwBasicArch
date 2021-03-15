package com.a15acdhmwbasicarch.presentation.mainActivity

import com.a15acdhmwbasicarch.data.PostsInfoRepository
import com.a15acdhmwbasicarch.tools.CoroutinesTestExtension
import com.a15acdhmwbasicarch.tools.InstantExecutorExtension
import com.a15acdhmwbasicarch.tools.getOrAwaitValueTest
import com.a15acdhmwbasicarch.tools.UpdatingState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.IOException

@ExperimentalCoroutinesApi
@ExtendWith(
    InstantExecutorExtension::class,
    CoroutinesTestExtension::class
)
internal class MainViewModelTest{

    @Test
    fun `success update local storage`(){
        val mockKRepository = mockk<PostsInfoRepository>{
            coEvery { updateLocalStorage() } returns Unit
        }

        val mainViewModel = MainViewModel(mockKRepository)
        mainViewModel.updateRepo()

        val liveDate = mainViewModel.errorLiveData.getOrAwaitValueTest()

        assertEquals(UpdatingState.COMPLETED, liveDate)
    }

    @Test
    fun `fail update local storage`(){
        val mockKRepository = mockk<PostsInfoRepository>{
            coEvery { updateLocalStorage() } throws IOException()
        }

        val mainViewModel = MainViewModel(mockKRepository)
        mainViewModel.updateRepo()

        val liveDate = mainViewModel.errorLiveData.getOrAwaitValueTest()

        assertEquals(UpdatingState.ERROR, liveDate)
    }
}
