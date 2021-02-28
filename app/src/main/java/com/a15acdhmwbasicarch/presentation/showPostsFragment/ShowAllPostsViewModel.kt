package com.a15acdhmwbasicarch.presentation.showPostsFragment

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.a15acdhmwbasicarch.data.PostsInfoRepository
import com.a15acdhmwbasicarch.domain.GetAllPostsUseCase
import com.a15acdhmwbasicarch.presentation.PostUiMapper
import com.a15acdhmwbasicarch.presentation.PostUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ShowAllPostsViewModel @Inject constructor(
    private val allPostsUseCase: GetAllPostsUseCase,
    private val postUiMapper: PostUiMapper
) : ViewModel() {

    private val _postsLiveData = MutableLiveData<List<PostUiModel>>()
    val postsLiveData: LiveData<List<PostUiModel>>
        get() = _postsLiveData

    @SuppressLint("CheckResult")
    fun getPosts() {
        allPostsUseCase.invoke()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map(postUiMapper::map)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                _postsLiveData.value = it
            }
    }
}