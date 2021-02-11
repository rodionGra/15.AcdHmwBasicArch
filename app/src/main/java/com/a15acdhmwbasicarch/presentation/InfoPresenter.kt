package com.a15acdhmwbasicarch.presentation

import android.util.Log
import com.a15acdhmwbasicarch.InfoView
import com.a15acdhmwbasicarch.R
import com.a15acdhmwbasicarch.domain.GetPostUseCase
import com.a15acdhmwbasicarch.threading.CancelableOperation
import com.a15acdhmwbasicarch.threading.Multithreading
import com.a15acdhmwbasicarch.tools.Result

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
        multithreading.async<Result<List<PostUiModel>, Int>> {
            val result: List<PostUiModel>? = getPostUseCase.invoke()
            return@async if (result != null) {
                Result.success(result)
            } else {
                Result.error(R.string.error_text)
            }
        }.postOnMainThread(::showResult)
    }

    private fun showResult(result: Result<List<PostUiModel>, Int>) {
        Log.d("showResult", "Result -> $result")
        if (result.isError) {
            view?.showError(result.errorResult)
        } else {
            view?.showUsersPost(result.successResult)
        }
    }
}