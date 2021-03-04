package com.a15acdhmwbasicarch.domain

import android.util.Log
import com.a15acdhmwbasicarch.R
import com.a15acdhmwbasicarch.data.AndroidResourceRepository
import com.a15acdhmwbasicarch.data.PostsInfoRepository
import com.a15acdhmwbasicarch.domain.model.NewPostModel
import javax.inject.Inject

const val TITLE_MIN_LENGTH = 3
const val TITLE_MAX_LENGTH = 50
const val BODY_MIN_LENGTH = 5
const val BODY_MAX_LENGTH = 500

class AddNewPostValidationUseCase @Inject constructor(
    private val postsInfoRepository: PostsInfoRepository,
    private val resource: AndroidResourceRepository,
) {
    suspend fun execute(postForSaving: NewPostModel): ValidationStatus<Set<NewPostErrorType>> {
        val setOfError: MutableSet<NewPostErrorType> = mutableSetOf()

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
            return ValidationStatus.Normal
        }

        return ValidationStatus.Error(setOfError)
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
