package com.a15acdhmwbasicarch.presentation.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a15acdhmwbasicarch.data.PostsInfoRepository
import com.a15acdhmwbasicarch.tools.UpdatingState
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val repository: PostsInfoRepository
) : ViewModel() {

    private val _errorLiveData = MutableLiveData<UpdatingState>()
    val errorLiveData
        get() = _errorLiveData as LiveData<UpdatingState>

    fun updateRepo() {
        //todo add errors handler
        viewModelScope.launch {
            repository.updateLocalStorage()
            _errorLiveData.value = UpdatingState.COMPLETED
        }
    }
}