package com.a15acdhmwbasicarch

import android.content.Context
import com.a15acdhmwbasicarch.data.DomainUserPostMapper
import com.a15acdhmwbasicarch.data.UserStatusLocalDataSource
import com.a15acdhmwbasicarch.domain.GetPostUseCase
import com.a15acdhmwbasicarch.presentation.InfoPresenter
import com.a15acdhmwbasicarch.presentation.PostUiMapper
import com.a15acdhmwbasicarch.threading.Multithreading
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PostComponent {
    fun createPresenter(context: Context): InfoPresenter {

        val infoUserPostRepository = InfoUserPostRepository(
            infoApiService = createService(),
            domainUserPostMapper = DomainUserPostMapper(UserStatusLocalDataSource().getSetOfStatusUser()),
        )

        val postUseCase = GetPostUseCase(infoUserPostRepository, PostUiMapper(ResourceRepository(context)))

        return InfoPresenter(Multithreading(context), postUseCase)
    }

    private fun createService(): InfoApiService {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(InfoApiService::class.java)
    }
}