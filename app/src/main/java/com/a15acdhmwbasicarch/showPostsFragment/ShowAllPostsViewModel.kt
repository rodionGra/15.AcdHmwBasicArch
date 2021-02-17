package com.a15acdhmwbasicarch.showPostsFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a15acdhmwbasicarch.domain.GetAllPostsUseCase
import com.a15acdhmwbasicarch.presentation.PostUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShowAllPostsViewModel @Inject constructor(
    private val allPostsUseCase: GetAllPostsUseCase
) : ViewModel() {

    val reposLiveData = MutableLiveData<List<PostUiModel>>()

    fun getPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            val result: List<PostUiModel>? = allPostsUseCase.invoke()
            withContext(Dispatchers.Main) {
                result?.also { posts ->
                    reposLiveData.postValue(posts)
                }
            }
        }
    }
}