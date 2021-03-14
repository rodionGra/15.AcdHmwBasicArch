package com.a15acdhmwbasicarch.presentation.showPostsFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a15acdhmwbasicarch.di.IoDispatcher
import com.a15acdhmwbasicarch.domain.GetAllSortedPostsUseCase
import com.a15acdhmwbasicarch.presentation.PostUiMapper
import com.a15acdhmwbasicarch.presentation.PostUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShowAllPostsViewModel @Inject constructor(
    private val allPostsUseCase: GetAllSortedPostsUseCase,
    private val postUiMapper: PostUiMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _postsLiveData = MutableLiveData<List<PostUiModel>>()
    val postsLiveData
        get() = _postsLiveData as LiveData<List<PostUiModel>>

    fun getPosts() {
        viewModelScope.launch {
            allPostsUseCase().map(postUiMapper::map).flowOn(ioDispatcher).collect {
                _postsLiveData.postValue(it)
            }
        }
    }
}
