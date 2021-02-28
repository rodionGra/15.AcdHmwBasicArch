package com.a15acdhmwbasicarch.presentation.mainActivity

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a15acdhmwbasicarch.data.PostsInfoRepository
import com.a15acdhmwbasicarch.tools.UpdatingState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: PostsInfoRepository
) : ViewModel() {

    private val _errorLiveData = MutableLiveData<UpdatingState>()
    val errorLiveData: LiveData<UpdatingState> = _errorLiveData

    @SuppressLint("CheckResult")
    fun updateRepo() {
        repository.updateLocalStorage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _errorLiveData.value = UpdatingState.COMPLETED },
                { _errorLiveData.value = UpdatingState.ERROR }
            )
    }
}