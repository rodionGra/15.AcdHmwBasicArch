package com.a15acdhmwbasicarch.presentation.createNewPostFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a15acdhmwbasicarch.domain.AddNewPostValidationUseCase
import com.a15acdhmwbasicarch.domain.model.NewPostModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateNewPostViewModel @Inject constructor(
    private val validationUseCase: AddNewPostValidationUseCase,
    private val mapInputErrorsToString: MapInputErrorsToString
) : ViewModel() {

    val stringErrorLiveData = MutableLiveData<String>()

    fun sendDataToCache(title: String, body: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val setInputErrors = validationUseCase.execute(NewPostModel(title, body))
            withContext(Dispatchers.Main) {
                if (setInputErrors.isEmpty()){
                    stringErrorLiveData.postValue("")
                }
                else{
                    stringErrorLiveData.postValue(mapInputErrorsToString.map(setInputErrors))
                }
            }
        }


    }

}