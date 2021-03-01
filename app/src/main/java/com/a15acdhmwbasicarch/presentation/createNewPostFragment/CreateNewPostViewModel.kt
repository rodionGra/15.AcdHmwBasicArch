package com.a15acdhmwbasicarch.presentation.createNewPostFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.a15acdhmwbasicarch.domain.AddNewPostValidationUseCase
import com.a15acdhmwbasicarch.domain.model.NewPostModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CreateNewPostViewModel @Inject constructor(
    private val validationUseCase: AddNewPostValidationUseCase,
    private val mapInputErrorsToString: MapInputErrorsToString,
    private val compositeDispose: CompositeDisposable
) : ViewModel() {

    val stringErrorLiveData = MutableLiveData<String>()

    fun sendDataToCache(title: String, body: String) {
        compositeDispose.add(
            validationUseCase.execute(NewPostModel(title, body))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it.isEmpty()) {
                            stringErrorLiveData.value = ""
                        } else {
                            stringErrorLiveData.value = mapInputErrorsToString.map(it)
                        }
                    },
                    {
                        Log.d("TAG", "${it.message}")
                    }
                )
        )
    }

    override fun onCleared() {
        compositeDispose.dispose()
        super.onCleared()
    }
}