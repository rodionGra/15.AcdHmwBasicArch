package com.a15acdhmwbasicarch.presentation

import android.util.Log
import com.a15acdhmwbasicarch.InfoView
import com.a15acdhmwbasicarch.Result
import com.a15acdhmwbasicarch.UserPostError
import com.a15acdhmwbasicarch.domain.GetPostUseCase
import com.a15acdhmwbasicarch.threading.CancelableOperation
import com.a15acdhmwbasicarch.threading.Multithreading

class InfoPresenter(
    private val multithreading: Multithreading,
    private val getPostUseCase: GetPostUseCase
) {
    private var view: InfoView? = null
    private var cancelableOperation: CancelableOperation? = null

    fun attachView(infoView: InfoView) {
        view = infoView
        getPosts()
    }

    fun detachView() {
        view = null
        cancelableOperation?.cancel()
    }

    private fun getPosts() {
        multithreading.async<Result<List<PostUiModel>, UserPostError>> {
            val result = getPostUseCase.funInvoke()
            return@async if (result != null) {
                Result.success(result)
            } else {
                Result.error(UserPostError.MAIN_USER_POST_LIST_NOT_LOADED)
            }
        }.postOnMainThread(::showResult)
    }

    private fun showResult(result: Result<List<PostUiModel>, UserPostError>) {
        Log.d("wtf", "result -> $result")
        if (result.isError) {
            view?.showError(result.errorResult)
        } else {
            view?.showInfo(result.successResult)
        }
    }
}