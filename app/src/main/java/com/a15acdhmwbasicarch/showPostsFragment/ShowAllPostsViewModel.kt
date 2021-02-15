package com.a15acdhmwbasicarch.showPostsFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a15acdhmwbasicarch.data.DomainUserPostMapper
import com.a15acdhmwbasicarch.data.PostsInfoRepository
import com.a15acdhmwbasicarch.AndroidResourceRepository
import com.a15acdhmwbasicarch.datasource.PostsCacheDataSource
import com.a15acdhmwbasicarch.datasource.UserStatusLocalDataSource
import com.a15acdhmwbasicarch.datasource.api.InfoApiService
import com.a15acdhmwbasicarch.domain.GetPostUseCase
import com.a15acdhmwbasicarch.presentation.PostUiMapper
import com.a15acdhmwbasicarch.presentation.PostUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShowAllPostsViewModel(application: Application) : AndroidViewModel(application) {

    val reposLiveData = MutableLiveData<List<PostUiModel>>()

    private val infoUserPostRepository = PostsInfoRepository(
        infoApiService = createService(),
        domainUserPostMapper = DomainUserPostMapper(UserStatusLocalDataSource().getSetOfStatusUser()),
        postsCacheDataSource = PostsCacheDataSource
    )

    private val postUseCase = GetPostUseCase(infoUserPostRepository, PostUiMapper(AndroidResourceRepository(application)))

    fun getPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            val result: List<PostUiModel>? = postUseCase.invoke()
            withContext(Dispatchers.Main) {
                result?.also { posts ->
                    reposLiveData.postValue(posts)
                }
            }
        }
    }

    private fun createService(): InfoApiService {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(InfoApiService::class.java)
    }
}