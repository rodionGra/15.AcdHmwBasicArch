package com.a15acdhmwbasicarch.presentation.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.a15acdhmwbasicarch.data.PostsInfoRepository
import com.a15acdhmwbasicarch.domain.ValidationStatus
import com.a15acdhmwbasicarch.tools.UpdatingState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val repository: PostsInfoRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    private val _errorLiveData = MutableLiveData<UpdatingState>()
    val errorLiveData
        get() = _errorLiveData as LiveData<UpdatingState>

    fun updateRepo() {
        compositeDisposable.add(
            repository.updateLocalStorage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _errorLiveData.value = UpdatingState.COMPLETED },
                    { _errorLiveData.value = UpdatingState.ERROR }
                )
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}