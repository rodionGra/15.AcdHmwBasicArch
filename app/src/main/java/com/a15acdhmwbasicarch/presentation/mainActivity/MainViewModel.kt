package com.a15acdhmwbasicarch.presentation.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a15acdhmwbasicarch.data.PostsInfoRepository
import com.a15acdhmwbasicarch.tools.UpdatingState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: PostsInfoRepository
) : ViewModel() {

    private val _errorLiveData = MutableLiveData<UpdatingState>()
    val errorLiveData
        get() = _errorLiveData as LiveData<UpdatingState>

    private val handler = CoroutineExceptionHandler { _, _ ->
        _errorLiveData.value = UpdatingState.ERROR
    }

    fun updateRepo() {
        viewModelScope.launch(handler) {
            repository.updateLocalStorage()
            _errorLiveData.value = UpdatingState.COMPLETED
        }
    }
}