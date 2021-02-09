package com.a15acdhmwbasicarch.presentation

import android.util.Log
import com.a15acdhmwbasicarch.InfoView
import com.a15acdhmwbasicarch.Result
import com.a15acdhmwbasicarch.UserPostError
import com.a15acdhmwbasicarch.domain.GetPostUseCase
import com.a15acdhmwbasicarch.threading.CancelableOperation

class InfoPresenter(
    private val getPostUseCase: GetPostUseCase
) {
    private var view: InfoView? = null
    private var cancelableOperation: CancelableOperation? = null

    fun attachView(infoView: InfoView) {
        view = infoView

        cancelableOperation = getPostUseCase.funInvoke().postOnMainThread(::showResult)
    }

    fun detachView() {
        view = null
        cancelableOperation?.cancel()
    }

    private fun showResult(result: Result<List<PostUiModel>, UserPostError>) {
        if (result.isError) {
            view?.showError(result.errorResult)
        } else {
            view?.showInfo(result.successResult)
        }
    }
}