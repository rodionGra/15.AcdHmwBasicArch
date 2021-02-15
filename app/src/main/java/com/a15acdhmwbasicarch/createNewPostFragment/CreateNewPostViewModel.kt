package com.a15acdhmwbasicarch.createNewPostFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.a15acdhmwbasicarch.datasource.PostsCacheDataSource

class CreateNewPostViewModel : ViewModel() {

    val validationUseCase  = AddNewPostValidationUseCase(PostsCacheDataSource)

    fun sendDataToCache(title: String, body: String){
        val list = validationUseCase.execute(title, body)
    }
}