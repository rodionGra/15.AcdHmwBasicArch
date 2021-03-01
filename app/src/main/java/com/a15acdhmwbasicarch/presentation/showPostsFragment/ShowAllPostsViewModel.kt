package com.a15acdhmwbasicarch.presentation.showPostsFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.a15acdhmwbasicarch.domain.GetAllPostsUseCase
import com.a15acdhmwbasicarch.domain.ValidationStatus
import com.a15acdhmwbasicarch.presentation.PostUiMapper
import com.a15acdhmwbasicarch.presentation.PostUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ShowAllPostsViewModel @Inject constructor(
    private val allPostsUseCase: GetAllPostsUseCase,
    private val postUiMapper: PostUiMapper,
    private val compositeDispose: CompositeDisposable
) : ViewModel() {

    private val _postsLiveData = MutableLiveData<List<PostUiModel>>()
    val postsLiveData
        get() = _postsLiveData as LiveData<List<PostUiModel>>


    fun getPosts() {
        compositeDispose.add(
            allPostsUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(postUiMapper::map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    _postsLiveData.value = it
                }
        )
    }

    override fun onCleared() {
        compositeDispose.dispose()
        super.onCleared()
    }
}