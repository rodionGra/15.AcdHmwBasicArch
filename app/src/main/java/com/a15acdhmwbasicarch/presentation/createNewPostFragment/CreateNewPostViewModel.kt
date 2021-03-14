package com.a15acdhmwbasicarch.presentation.createNewPostFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a15acdhmwbasicarch.domain.AddNewPostValidationUseCase
import com.a15acdhmwbasicarch.domain.VerificationStatus
import com.a15acdhmwbasicarch.domain.model.NewPostModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateNewPostViewModel @Inject constructor(
    private val validationUseCase: AddNewPostValidationUseCase,
    private val mapInputErrorsToString: MapInputErrorsToString
) : ViewModel() {

    private val _stringErrorLiveData = MutableLiveData<VerificationStatus<String>>()
    val stringErrorLiveData
        get() = _stringErrorLiveData as LiveData<VerificationStatus<String>>

    fun sendDataToCache(title: String, body: String) {
        viewModelScope.launch {
            _stringErrorLiveData.value =
                when (val result = validationUseCase(NewPostModel(title, body))) {
                    is VerificationStatus.Normal -> VerificationStatus.Normal
                    is VerificationStatus.Error -> VerificationStatus.Error(
                        mapInputErrorsToString(result.errors)
                    )
                }
        }
    }
}
