package com.a15acdhmwbasicarch.domain

import android.annotation.SuppressLint
import android.util.Log
import com.a15acdhmwbasicarch.data.AndroidResourceRepository
import com.a15acdhmwbasicarch.R
import com.a15acdhmwbasicarch.data.PostsInfoRepository
import com.a15acdhmwbasicarch.domain.model.NewPostModel
import io.reactivex.Single
import javax.inject.Inject

const val TITLE_MIN_LENGTH = 3
const val TITLE_MAX_LENGTH = 50
const val BODY_MIN_LENGTH = 5
const val BODY_MAX_LENGTH = 500

class AddNewPostValidationUseCase @Inject constructor(
    private val postsInfoRepository: PostsInfoRepository,
    private val resource: AndroidResourceRepository,
) {
    @SuppressLint("CheckResult")
    fun execute(postForSaving: NewPostModel): Single<Set<NewPostErrorType>> {
        val setOfError: MutableSet<NewPostErrorType> = mutableSetOf()

        return Single.create<Set<NewPostErrorType>> {

            Log.d("TAG", "STart")

            if (postForSaving.title.length < TITLE_MIN_LENGTH) {
                setOfError.add(NewPostErrorType.TITLE_LENGTH_MIN_ERROR)
            }

            if (postForSaving.title.length > TITLE_MAX_LENGTH) {
                setOfError.add(NewPostErrorType.TITLE_LENGTH_MAX_ERROR)
            }

            if (postForSaving.body.length < BODY_MIN_LENGTH) {
                setOfError.add(NewPostErrorType.BODY_LENGTH_MIN_ERROR)
            }

            if (postForSaving.body.length > BODY_MAX_LENGTH) {
                setOfError.add(NewPostErrorType.BODY_LENGTH_MAX_ERROR)
            }

            if (checkForbiddenWords(postForSaving.title)) {
                setOfError.add(NewPostErrorType.FORBIDDEN_WORDS_ERROR)
            }

            if (setOfError.isEmpty()) {
                postsInfoRepository.saveNewPostFromUser(postForSaving)
            }

            it.onSuccess(setOfError)
        }
    }

    private fun checkForbiddenWords(title: String): Boolean {
        for (word in resource.getStringArray(R.array.forbidden_words)) {
            if (title.contains(word, true)) {
                return true
            }
        }
        return false
    }
}
