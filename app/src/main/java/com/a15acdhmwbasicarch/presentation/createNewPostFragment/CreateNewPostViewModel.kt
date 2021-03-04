package com.a15acdhmwbasicarch.presentation.createNewPostFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a15acdhmwbasicarch.domain.AddNewPostValidationUseCase
import com.a15acdhmwbasicarch.domain.ValidationStatus
import com.a15acdhmwbasicarch.domain.model.NewPostModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateNewPostViewModel @Inject constructor(
    private val validationUseCase: AddNewPostValidationUseCase,
    private val mapInputErrorsToString: MapInputErrorsToString
) : ViewModel() {

    private val _stringErrorLiveData = MutableLiveData<ValidationStatus<String>>()
    val stringErrorLiveData
        get() = _stringErrorLiveData as LiveData<ValidationStatus<String>>

    fun sendDataToCache(title: String, body: String) {
        viewModelScope.launch {
            when (val result = validationUseCase.execute(NewPostModel(title, body))) {
                is ValidationStatus.Normal -> _stringErrorLiveData.value = ValidationStatus.Normal
                is ValidationStatus.Error -> _stringErrorLiveData.value =
                    ValidationStatus.Error(mapInputErrorsToString.map(result.errors))
            }
        }
    }
}